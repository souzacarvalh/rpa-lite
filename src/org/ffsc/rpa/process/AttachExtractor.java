package org.ffsc.rpa.process;

import java.io.InputStream;

import javax.mail.Part;

public interface AttachExtractor {

	public boolean extract(Part anexo);

	public void saveExtractedFile(InputStream is, String name,
			Boolean saveAsCharacter);

}
