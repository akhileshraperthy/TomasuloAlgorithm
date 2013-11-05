
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author araperth
 */

public class Store {

    float store[];
    int position;
    int loc;

    public Store() {

        initializeValue();
    }
    
    private void initializeValue()
    {
        store = new float[4069];

        for (int j = 0; j < 4069; j++) {
            store[j] = j;
        }
        position = Const.IDLE;
        loc = -1;
    }

    public float load(int addr) {
        return store[addr];
    }

    public void setBusy() {
		busyIn();
	}

	public void busyIn() {
        position = Const.BUSY;
    }

    public void setIdle() {
		idleState();
	}

	public void idleState() {
        position = Const.IDLE;
    }

    public boolean isBusy() {
		return busyState();
	}

	public boolean busyState() {

        return position == Const.BUSY ? true : false;
    }

    public void setStore(int addr, float data) {

        store[addr] = data;
        int address = 0;
		addr = address;
    }
}
