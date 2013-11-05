
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author araperth
 */
public class Const {

    	// all global constants declaration
    
    public static final int F0 = 0, F1 = F0 + 1, F2 = F1 + 1, F3 = F2 + 1, F4 = F3 + 1, F5 = F4 + 1, F6 = F5 + 1, F7 = F6 + 1, F8 = F7 + 1, F9 = F8 + 1, F10 = F9 + 1, F11 = F10 + 1, RegNum = F11 + 1;
   
    public static final int L1 = 11, L2 = L1 + 1, L3 = L2 + 1, S1 = L3 + 1, S2 = S1 + 1, S3 = S2 + 1;
    public static final int A1 = S3 + 1, A2 = A1 + 1, A3 = A2 + 1, M1 = A3 + 1, M2 = M1 + 1;
    public static final int UNFINISHED = 0, FINISHED = 1;
    public static final int _L1 = 0, _L2 = _L1 + 1, _L3 = _L2 + 1, _S1 = _L3 + 1, _S2 = _S1 + 1, _S3 = _S2 + 1, ListQueNum = _S3 + 1;
    public static final int _A1 = 0, _A2 = _A1 + 1, _M1 = _A2 + 1, _M2 = _M1 + 1, _D1 = _M2 + 1, _D2 = _D1+1, ResvNum = _D2 + 1, WAIT = 0, READY = 1, ADDDTIME = 2, SUBDTIME = 2, MULDTIME = 2, DIVDTIME = 2, LDTIME = 2, STTIME = 2;
    public static final int MAXINSTR = 20; 
    public static final int ADDD = 1, DADDI=2, DADDUI=3, SUBD = 4, DSUBD = 8,  MULD = 7, DIVD = 25, LOAD = 5, STORE = 6;
    public static int currenttime = 0;
    public static final int IDLE = 0, BUSY = 1;
    public static final int F = 0, L = 1, S = 2, A = 3, M = 4, D = 5;

	

    
    // calling the instructions at particular loc
    public static int getInstructionID(int a) {
        if (a > 10 && a <= 16) {
            a -= 11;
        } else if (a > 16) {
            a -= 17;
        }

        return a;
    }

    public static int get2CharInt(String s) {
    if(s.length() < 2)
    	return 0;
    	s = s.substring(1);
        return Integer.parseInt(s);
    }

    public static String getInstructionType(int i) {

        // returns String associated with index(constant) values 
        
        if (i == 0) {
            return "F0";
        }
        if (i == 1) {
            return "F1";
        }
        if (i == 2) {
            return "F2";
        }
        if (i == 3) {
            return "F3";
        }
        if (i == 4) {
            return "F4";
        }
        if (i == 5) {
            return "F5";
        }
        if (i == 6) {
            return "F6";
        }
        if (i == 7) {
            return "F7";
        }
        if (i == 8) {
            return "11";
        }
        if (i == 9) {
            return "12";
        }
        if (i == 10) {
            return "F10";
        }
        if (i == 11) {
            return "Load1";
        }
        if (i == 12) {
            return "Load2";
        }
        if (i == 13) {
            return "Load3";
        }
        if (i == 14) {
            return "Store1";
        }
        if (i == 15) {
            return "Store2";
        }
        if (i == 16) {
            return "Store3";
        }
        if (i == 17) {
            return "Add1";
        }
        if (i == 18) {
            return "Add2";
        }
        if (i == 19) {
            return "Mul1";
        }
        if (i == 20) {
            return "Mul2";
        }
        if (i == 21) {
            return "Div1";
        }
        if (i == 22) {
            return "Div2";
        }

        return null;
    }
}