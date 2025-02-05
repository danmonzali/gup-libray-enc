package it.tim.enc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.tim.enc.exception.EncryptDecryptException;
import it.tim.enc.pojo.BaseEnc;
import it.tim.enc.pojo.EncryptPojo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
	
@Service
public class EncryptService extends BaseEnc {

	private static final Logger logger = LogManager.getLogger(EncryptService.class);

	private static final String CRYPTO_UTILS_CRYPT = "CRYPTO_UTILS.CRYPT";

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${master.key.encrypt.decrypt}")
	private String masteeKeyEncryptDecrypt;

	@Transactional
	public EncryptPojo encryptText(EncryptPojo encryptPojo) throws EncryptDecryptException {
		if (encryptPojo == null) {
			throw new EncryptDecryptException("Nessun dato da cryptare");
		}

		try {
			// Crea una StoredProcedureQuery per la funzione PL/SQL
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CRYPTO_UTILS_CRYPT);
			// PROCEDURE CRYPT(CLEAR_TEXT IN VARCHAR2, MASTER_KEY IN VARCHAR2, KEY_GROUP IN
			// VARCHAR2, KEY_ID OUT VARCHAR2, IV OUT VARCHAR2,CIPHER_TEXT OUT VARCHAR2);
			// Registrare i parametri di input
			query.registerStoredProcedureParameter(CLEAR_TEXT, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(MASTER_KEY, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(KEY_GROUP, String.class, ParameterMode.IN);

			// Registrare i parametri di output
			query.registerStoredProcedureParameter(KEY_ID, String.class, ParameterMode.OUT);
			query.registerStoredProcedureParameter(IV, String.class, ParameterMode.OUT);
			query.registerStoredProcedureParameter(CIPHER_TEXT, String.class, ParameterMode.OUT);

			// Impostare i parametri di input
			query.setParameter(CLEAR_TEXT, encryptPojo.getClearText());
			query.setParameter(MASTER_KEY, masteeKeyEncryptDecrypt);
			query.setParameter(KEY_GROUP, encryptPojo.getKeyGroup());

			// Eseguire la funzione e ottenere il risultato
			query.execute();

			// Ottenere i parametri di output
			String keyId = (String) query.getOutputParameterValue(KEY_ID);
			String iv = (String) query.getOutputParameterValue(IV);
			String chiperText = (String) query.getOutputParameterValue(CIPHER_TEXT);

			encryptPojo.setKeyId(keyId);
			encryptPojo.setIv(iv);
			encryptPojo.setChiperText(chiperText);

			return encryptPojo;

		} catch (Throwable t) {
			logger.error("Errore in fase di encrypt: " + encryptPojo.toString());
			throw new EncryptDecryptException("Errore in fase di encrypt: " + encryptPojo.toString());
		}
	}
	
	
//	public static void main(String[] args) {
//		String iban = "IT60X0542811101000000123456";
//		
//	}

}
