package it.tim.enc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.tim.enc.pojo.BaseEnc;
import it.tim.enc.pojo.EncryptPojo;

@Service
public class EncryptService extends BaseEnc {
	private static final String CRYPTO_UTILS_CRYPT = "CRYPTO_UTILS.CRYPT";
	@PersistenceContext
	private EntityManager entityManager;
	@Value("${master.key.bdata}")
	private String masterKeyBdata;

	@Transactional
	public EncryptPojo encryptText(EncryptPojo encryptPojo) {

		// Crea una StoredProcedureQuery per la funzione PL/SQL
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CRYPTO_UTILS_CRYPT);
		// PROCEDURE CRYPT(CLEAR_TEXT IN VARCHAR2, MASTER_KEY IN VARCHAR2, KEY_GROUP IN
		// VARCHAR2, KEY_ID OUT VARCHAR2, IV OUT VARCHAR2,CIPHER_TEXT OUT VARCHAR2);
		// Registrare i parametri di input
		query.registerStoredProcedureParameter(CLEAR_TEXT, String.class, javax.persistence.ParameterMode.IN);
		query.registerStoredProcedureParameter(MASTER_KEY, String.class, javax.persistence.ParameterMode.IN);
		query.registerStoredProcedureParameter(KEY_GROUP, String.class, javax.persistence.ParameterMode.IN);

		// Registrare i parametri di output
		query.registerStoredProcedureParameter(KEY_ID, String.class, javax.persistence.ParameterMode.OUT);
		query.registerStoredProcedureParameter(IV, String.class, javax.persistence.ParameterMode.OUT);
		query.registerStoredProcedureParameter(CIPHER_TEXT, String.class, javax.persistence.ParameterMode.OUT);

		// Impostare i parametri di input
		query.setParameter(CLEAR_TEXT, encryptPojo.getClearText());
		query.setParameter(MASTER_KEY, masterKeyBdata);
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

		// Stampare o utilizzare i valori di KEY_ID e IV come necessario
		System.out.println("CIPHER_TEXT: " + chiperText);

		return encryptPojo;
	}

}
