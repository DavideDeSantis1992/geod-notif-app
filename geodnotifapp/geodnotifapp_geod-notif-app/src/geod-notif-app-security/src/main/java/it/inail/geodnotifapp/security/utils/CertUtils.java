package it.inail.geodnotifapp.security.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class CertUtil.
 */
public final class CertUtils {

	private static final String BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----";

	private static final String END_CERTIFICATE = "-----END CERTIFICATE-----";

	/** The certificates. */
	private static Map<String, Certificate> certificates;

	public static synchronized Map<String, Certificate> getCertificate(ResourceLoader resourceLoader, String certificatePath) throws CertificateException, IOException {
		if (certificates == null) {
			certificates = loadCertificate(resourceLoader, certificatePath);
		}
		return certificates;
	}

	private static Map<String, Certificate> loadCertificate(ResourceLoader resourceLoader, String certPAth) throws CertificateException, IOException {
		Map<String, Certificate> certificates = new HashMap<>();
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		Resource resource = resourceLoader.getResource(certPAth);
		if (resource.isFile()) {
			X509Certificate certificate = (X509Certificate)factory.generateCertificate(resource.getInputStream());
			certificates.put(certificate.getIssuerDN().getName(), certificate);
		} else {
			File dir = resource.getFile();
			File[] files = dir.listFiles();
			for (File file : files) {
				Resource res = resourceLoader.getResource(file.getPath());
				X509Certificate certificate = (X509Certificate)factory.generateCertificate(res.getInputStream());
				certificates.put(certificate.getIssuerDN().getName(), certificate);
			}
		}
		return certificates;

	}

	private static String buildCertificateString(String certificateBody) {
		if (certificateBody == null || certificateBody.length() == 0) throw new IllegalArgumentException("Certificate body is null or empty");
		if (certificateBody.startsWith(BEGIN_CERTIFICATE) && certificateBody.endsWith(END_CERTIFICATE)) return certificateBody;
		return new StringBuffer().append(BEGIN_CERTIFICATE)
				.append("\n")
				.append(certificateBody)
				.append("\n")
				.append(END_CERTIFICATE)
				.toString();
	}

	public static X509Certificate buildCertificateFromString(String certificateBody) throws CertificateException, IOException {
		String certificateString = buildCertificateString(certificateBody);
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		try (InputStream certStream = new ByteArrayInputStream(certificateString.getBytes())) {
			X509Certificate certificate = (X509Certificate) factory.generateCertificate(certStream);
			return certificate;
		}
	}

	public static X509Certificate getCertificateFromFile(InputStream stream) throws CertificateException, IOException {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		return (X509Certificate)factory.generateCertificate(stream);
	}

}
