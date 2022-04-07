package Projet;

import java.util.Random;

public class Instruction {
	
	private enum InstructionType{
		RIEN,MEUBLE,LOT;
		public static InstructionType generate() {
			InstructionType[] val = InstructionType.values();
			return val[new Random().nextInt(val.length)];
		};
	}
	
	private InstructionType type;

	
	public Instruction(InstructionType t) {
		this.type = t;
	}
	public Instruction() {
		this.type = InstructionType.generate();
	}
	
	
	
	
	
	public Instruction generateRandom() {
		Instruction res = new Instruction();
		switch (res.type) {
		case RIEN:
			break;
		case MEUBLE:
			return res;
		case LOT:
			return res;
		}
		return res;
	}

}
