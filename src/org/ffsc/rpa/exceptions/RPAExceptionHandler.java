package org.ffsc.rpa.exceptions;

import org.ffsc.rpa.ui.WindowManager;

public class RPAExceptionHandler implements
		ExceptionHandler<RPAException, Exception> {

	@Override
	public RPAException handle(Exception exception) {

		String message = "Houve um erro ao executar esta operação. "
							+ "Verifique o log para maiores detalhes";
		
		if(exception instanceof RPABusinessException){
			
			message = exception.getMessage();
		
			WindowManager.showWarningMessage(message);
			
		} else {
			WindowManager.showErrorMessage(message);
		}	

		exception.printStackTrace();
		
		return new RPAException("RPA Application Exception: " + exception.getMessage());
	}
}