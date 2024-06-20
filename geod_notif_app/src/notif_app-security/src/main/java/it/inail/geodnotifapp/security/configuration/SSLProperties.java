package it.inail.geodnotifapp.security.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "inail.ssl")
public class SSLProperties {

    private Boolean hostnameVerify = Boolean.TRUE;

    private Boolean trustAllCertificates = Boolean.FALSE;

    private Boolean loadCertificateFromGateway = Boolean.TRUE;

    private CertificateProperties certsFromFolder = new CertificateProperties(Boolean.FALSE, null, null);

    private CertificateProperties customCaCerts = new CertificateProperties(Boolean.FALSE, null, null);

    public static class CertificateProperties {

        private Boolean enabled;

        private String path;

        private String password;

        public CertificateProperties(Boolean enabled, String path, String password) {
            this.enabled = enabled;
            this.path = path;
            this.password = password;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public SSLProperties() {
		super();
	}

	public Boolean getHostnameVerify() {
        return hostnameVerify;
    }

    public void setHostnameVerify(Boolean hostnameVerify) {
        this.hostnameVerify = hostnameVerify;
    }

    public Boolean getTrustAllCertificates() {
        return trustAllCertificates;
    }

    public void setTrustAllCertificates(Boolean trustAllCertificates) {
        this.trustAllCertificates = trustAllCertificates;
    }

    public Boolean getLoadCertificateFromGateway() {
        return loadCertificateFromGateway;
    }

    public void setLoadCertificateFromGateway(Boolean loadCertificateFromGateway) {
        this.loadCertificateFromGateway = loadCertificateFromGateway;
    }

    public CertificateProperties getCertsFromFolder() {
        return certsFromFolder;
    }

    public void setCertsFromFolder(CertificateProperties certsFromFolder) {
        this.certsFromFolder = certsFromFolder;
    }

    public CertificateProperties getCustomCaCerts() {
        return customCaCerts;
    }

    public void setCustomCaCerts(CertificateProperties customCaCerts) {
        this.customCaCerts = customCaCerts;
    }

}
