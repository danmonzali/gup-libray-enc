package it.tim.enc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.tim.enc.pojo.BaseEnc;
import it.tim.enc.pojo.DecryptPojo;

@Service
public class DecryptService extends BaseEnc {

	private static final String CRYPTO_UTILS_DECRYPT = "CRYPTO_UTILS.DECRYPT";
	@PersistenceContext
	private EntityManager entityManager;

	@Value("${master.key.bdata}")
	private String masterKeyBdata;

	@Value("${master.key.ccc}")
	private String masterKeyCCC;

	@Transactional
	public String decryptText(DecryptPojo decryptPojo) {

		// Crea una StoredProcedureQuery per la funzione PL/SQL
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CRYPTO_UTILS_DECRYPT);
		// PROCEDURE CRYPT(CLEAR_TEXT IN VARCHAR2, MASTER_KEY IN VARCHAR2, KEY_GROUP IN
		// VARCHAR2, KEY_ID OUT VARCHAR2, IV OUT VARCHAR2,CIPHER_TEXT OUT VARCHAR2);
		// Registrare i parametri di input
		query.registerStoredProcedureParameter(CIPHER_TEXT, String.class, javax.persistence.ParameterMode.IN);
		query.registerStoredProcedureParameter(MASTER_KEY, String.class, javax.persistence.ParameterMode.IN);
		query.registerStoredProcedureParameter(KEY_NAME, String.class, javax.persistence.ParameterMode.IN);
		query.registerStoredProcedureParameter(IV, String.class, javax.persistence.ParameterMode.IN);

		// Registrare i parametri di output
		query.registerStoredProcedureParameter(CLEAR_TEXT, String.class, javax.persistence.ParameterMode.OUT);

		// Impostare i parametri di input
		query.setParameter(CIPHER_TEXT, decryptPojo.getChiperText());
		query.setParameter(MASTER_KEY, masterKeyBdata);
		query.setParameter(KEY_NAME, decryptPojo.getKeyName());
		query.setParameter(IV, decryptPojo.getIv());

		// Eseguire la funzione e ottenere il risultato
		query.execute();

		// Ottenere i parametri di output
		String clearText = (String) query.getOutputParameterValue(CLEAR_TEXT);

		return clearText;
	}

	@Transactional
	public String decryptTextCCC(DecryptPojo decryptPojo) {

		// Crea una StoredProcedureQuery per la funzione PL/SQL
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CRYPTO_UTILS_DECRYPT);
		// PROCEDURE CRYPT(CLEAR_TEXT IN VARCHAR2, MASTER_KEY IN VARCHAR2, KEY_GROUP IN
		// VARCHAR2, KEY_ID OUT VARCHAR2, IV OUT VARCHAR2,CIPHER_TEXT OUT VARCHAR2);
		// Registrare i parametri di input
		query.registerStoredProcedureParameter(CIPHER_TEXT, String.class, javax.persistence.ParameterMode.IN);
		query.registerStoredProcedureParameter(MASTER_KEY, String.class, javax.persistence.ParameterMode.IN);
		query.registerStoredProcedureParameter(KEY_NAME, String.class, javax.persistence.ParameterMode.IN);
		query.registerStoredProcedureParameter(IV, String.class, javax.persistence.ParameterMode.IN);

		// Registrare i parametri di output
		query.registerStoredProcedureParameter(CLEAR_TEXT, String.class, javax.persistence.ParameterMode.OUT);

		// Impostare i parametri di input
		query.setParameter(CIPHER_TEXT, decryptPojo.getChiperText());
		query.setParameter(MASTER_KEY, masterKeyCCC);
		query.setParameter(KEY_NAME, decryptPojo.getKeyName());
		query.setParameter(IV, decryptPojo.getIv());

		// Eseguire la funzione e ottenere il risultato
		query.execute();

		// Ottenere i parametri di output
		String clearText = (String) query.getOutputParameterValue(CLEAR_TEXT);

		return clearText;
	}

}
