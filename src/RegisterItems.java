
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author araperth
 */

class RegisterItems {

    int position;
    int loc;
    float data;

    public RegisterItems() {

        initializeState();
    }
    
    // conditions for each position , data and loc inside function
    private void initializeState()
    {
        position = Const.IDLE;
        loc = -1;
        data = 0f;
    }
// reservation location or FU is busy 
    public boolean isBusy() {
        if (position == Const.BUSY) {
            return true;
        }
        return false;
    }

    public void setBusy() {
        position = Const.BUSY;
    }

    public void setIdle() {
		idleState();
	}

	public void idleState() {
        position = Const.IDLE;
    }

    public float read() {
        return data;
    }

    public void write(float data) {
        this.data = data;
    }

    public void setLoc(int s) {
        loc = s;
    }

    public int getLoc() {
        return loc;
    }
}