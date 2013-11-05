
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author araperth
 */

//float register here 
public class RegisterFloatvalues {

    RegisterItems reg[];

    public RegisterFloatvalues() {

        initializeRegisters();

    }

    private void initializeRegisters() {
        reg = new RegisterItems[Const.RegNum];

        for (int i = 0; i < Const.RegNum; i++) {


            reg[i] = new RegisterItems();
            reg[i].write(0);//  default vale here

        }
    }

    public float Instr_read(int id) {

        return reg[id].read();

    }

    public void Instr_write(int id, float data) {

        reg[id].write(data);
    }

    public boolean BusyInstr(int id) {

        return reg[id].isBusy();
    }

    public void setBusy(int id) {
        reg[id].setBusy();
    }

    public void setIdle(int id) {
		idleState(id);
	}

	public void idleState(int id) {
        reg[id].idleState();
    }

    public void setStation(int id, int station) {
        reg[id].setLoc(station);
    }

    public int getStation(int id) {

        return reg[id].getLoc();
    }
}
