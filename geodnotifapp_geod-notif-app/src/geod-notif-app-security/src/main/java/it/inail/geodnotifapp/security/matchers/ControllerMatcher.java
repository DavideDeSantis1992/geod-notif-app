package it.inail.geodnotifapp.security.matchers;

import it.inail.geodnotifapp.security.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * La classe di utilità che si occupa di trovare i path di tutte le classi Rest Controller presenti nel servizio,
 * necessarie per la configurazione di sicurezza.
 */
@Component
public class ControllerMatcher implements BasicMatcher {

    private static final String BASE_PACKAGE = "it.inail.geodnotifapp";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    private Class<? extends Annotation>[] annotationTypes = new Class[] {
            GetMapping.class,
            PatchMapping.class,
            PostMapping.class,
            PutMapping.class,
            DeleteMapping.class,
            DeleteMapping.class,
            RequestMapping.class
    };

    @Override
    public Map<String, RequestMatcher> getMatchers() throws ClassNotFoundException {

        Map<String, RequestMatcher> matchers = new HashMap<>();

        ClassPathScanningCandidateComponentProvider classPathScanningCandidateComponentProvider =
                new ClassPathScanningCandidateComponentProvider(false);
        classPathScanningCandidateComponentProvider.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        Set<BeanDefinition> candidateComponents = classPathScanningCandidateComponentProvider.findCandidateComponents(BASE_PACKAGE);

        for (BeanDefinition beanDefinition : candidateComponents) {
            Class<?> beanClass = ClassUtils.forName(beanDefinition.getBeanClassName(), this.getClass().getClassLoader());
            String[] beanNamesForType = applicationContext.getBeanNamesForType(beanClass);
            RequestMapping annotationOnBean = applicationContext.findAnnotationOnBean(beanNamesForType[0], RequestMapping.class);
            if (annotationOnBean != null) {
                addRequestMatcherIfNecessary(matchers, annotationOnBean);
            }else {
                Method[] methods = beanClass.getMethods();
                for (Method method : methods) {
                    addRequestMatcherIfNecessary(matchers, method, annotationTypes);
                }
            }
        }

        return matchers;
    }

    private void addRequestMatcherIfNecessary(Map<String, RequestMatcher> requestMatchers, Method method, Class<? extends Annotation>... annotationTypes) {
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            if (method.isAnnotationPresent(annotationType)) {
                Annotation annotation = AnnotationUtils.getAnnotation(method, annotationType);
                addRequestMatcherIfNecessary(requestMatchers, annotation);
            }
        }
    }

    private void addRequestMatcherIfNecessary(Map<String, RequestMatcher> requestMatchers, Annotation annotation) {
        String[] value = (String[]) AnnotationUtils.getValue(annotation, "value");
        for (String path : value) {

            if (path.startsWith("${") && path.endsWith("}")) {
                String tmp = environment.resolvePlaceholders(path);
                if (tmp == null) throw new IllegalArgumentException("Unable to resolve parametric path: "+ tmp);
                path = tmp;
            }

            path = PathUtils.getPath(path);

            int i = path.indexOf('/', 1);
            if (i != -1) {
                path = path.substring(0, i);
            }

            if (!requestMatchers.containsKey(path)) {
                requestMatchers.put(path, new AntPathRequestMatcher(path + "/**"));
            }
        }
    }
}
