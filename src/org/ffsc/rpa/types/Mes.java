package org.ffsc.rpa.types;

public enum Mes {

	JANEIRO(1, "Janeiro"),
	FEVEREIRO(2, "Fevereiro"),
	MARCO(3, "Março"),
	ABRIL(4, "Abril"),
	MAIO(5, "Maio"),
	JUNHO(6, "Junho"),
	JULHO(7, "Julho"),
	AGOSTO(8, "Agosto"),
	SETEMBRO(9, "Setembro"),
	OUTUBRO(10, "Outubro"),
	NOVEMBRO(11, "Novembro"),
	DEZEMBRO(12, "Dexembro");
	
	private Integer index;
	private String label;
	
	private Mes(Integer index, String label){
		this.index = index;
		this.label = label;
	}

	public Integer getIndex() {
		return index;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static Mes getByIndex(int index){
		
		for(Mes m: values()){
			if(m.getIndex() == (index + 1)){
				return m;
			}
		}
		
		return null;
	}
}