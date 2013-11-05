
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author araperth
 */

public class Registerhold {

	InstructionRegisters resv_station[];

	public Registerhold(){

		initializeRegister();
	}
        
        private void initializeRegister()
        {
            resv_station = new InstructionRegisters[Const.ResvNum];
		for (int i = 0; i < Const.ResvNum; i++){
			resv_station[i] = new InstructionRegisters ();
		}
        }
// calling the values and storing them accordingly in their respective stations
	public String getOp(int i){
		return resv_station[i].operVal;
	}

	public void setOp(int id, String op){
		operVal(id, op);
	}

	public void operVal(int id, String op){
		resv_station[id].operVal = op;
	}

	public void setResult(int id, float f){
		setVal(id, f);
	}

	public void setVal(int id, float f){
		resv_station[id].endRes = f;
	}

	public float getResult(int id){
		return getVal(id);
	}

	public float getVal(int id){
		return resv_station[id].endRes;
	}

	public boolean _isBusy(int id){
            return resv_station[id].ctrl == Const.BUSY? true:false;
	}

	public void setStation1(int id, int station){
		setLoc1(id, station);
	}

	public void setLoc1(int id, int station){
		resv_station[id].loc1 = station;
	}

	public void setStation2(int id, int station){
		resv_station[id].loc2 = station;
	}
//Reservation stations producing the corresponding source operand.
	public void setData1(int id, float data){
		resv_station[id].val1 = data;
	}

	public void setData2(int id, float data){
		resv_station[id].val2 = data;
	}

	public void setBusy(int id){
		resv_station[id].ctrl = Const.BUSY;
	}

	public void setIdle(int id){
		resv_station[id].ctrl = Const.IDLE;
	}

	public int getStation1(int id){
		return resv_station[id].loc1;
	}

	public int getStation2(int id){
		return resv_station[id].loc2;
	}

	public float getData1(int id){
		return resv_station[id].val1;
	}

	public float getData2(int id){
		return resv_station[id].val2;
	}
}

