/**
 *
 * @author Nabeel Ahsan
 */
public class Routine {
    String routine[][] = new String[5][6];
    int row;
    int col;
    void setSize(int x,int y) {
        row = x;
        col = y;
        String Resize[][] = new String[x][y];
        for(int i=0;i<row;i++){
            if(i>=5) break;
            for(int j=0;j<col;j++){
                if(j>=6) break;
                Resize[i][j] = routine[i][j]; 
            }
        }
        this.routine = Resize;
    }
    boolean resize = false;
    String getAlphaNumericString(int n) 
    {  
	     String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	            + "0123456789"
	            + "abcdefghijklmnopqrstuvxyz"; 
	    
	     StringBuilder sb = new StringBuilder(n); 
	    
	     for (int i = 0; i < n; i++) { 
	      int index 
	       = (int)(AlphaNumericString.length() 
	         * Math.random());
	      sb.append(AlphaNumericString 
	         .charAt(index)); 
	     } 
	    
	     return sb.toString(); 
    } 
    void ClearRoutine(){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(this.routine[i][j] != null && !this.routine[i][j].contains("BUSY")){
                    this.routine[i][j] = null;
                }
                
            }
        }
    }
    void ShowRoutine(){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                System.out.print(this.routine[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}
