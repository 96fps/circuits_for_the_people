import java.awt.Color;
import java.awt.Graphics;


public class circuitEditorScene extends Scene{
	double timestep=0;
	pos2d dim;
	circuitDesign circ;
	
	boolean[][][] simstate;

	double timelast=0;
	
	public circuitEditorScene(pos2d board_dim){
		dim = board_dim;
		circ= new circuitDesign(dim);
		simstate = new boolean[2][(int)dim.x][(int)dim.y];
		simstate[1][2][3]=true;
		circ.stdTemplate();
	}
	public void dologicTick(Controlset c, double itter){
		//do logic
		timestep+=itter;
		
		if(timestep>timelast+2 && true){
			circ= new circuitDesign(dim);
			timelast = timestep;
			simstate = new boolean[2][(int)dim.x][(int)dim.y];
			simstate[1][2][3]=true;
			circ.stdTemplate();
		}
		simstate[0]=simstate[1];
		simstate[1] = new boolean[(int)dim.x][(int)dim.y];
		for(int i=0; i<(int)dim.x; i++){
			for(int j=0; j<(int)dim.y-1; j++){
				if(simstate[0][i][j] && circ.metal_layer[i][j]){
					simstate[1][i][j]=true;
					
					if(simstate[0][i][j] && circ.metal_H[i][j]){
						simstate[1][(i+1)%(int)dim.x][j]=true;
					}
					if(simstate[0][i][j] && circ.metal_H[(i+(int)dim.x-1)%(int)dim.x][j]){
						simstate[1][(i-1)%(int)dim.x][j]=true;
					}
					if(simstate[0][i][j] && circ.metal_V[i][j]){
						simstate[1][i][(j+1)%(int)dim.y]=true;
					}
					if(simstate[0][i][j] && circ.metal_V[i][((int)dim.y+j-1)%(int)dim.y]){
						simstate[1][i][(j-1)%(int)dim.y]=true;
					}
				}
			
			}
		}
		
		
	}
	public void render(Graphics g, pos2d gameRes){
		this.render(g, gameRes,10);
	}
	public void render(Graphics g, pos2d gameRes,int t){
		
		g.setColor(new Color(128,128,128));
		g.fillRect(0, 0, (int)gameRes.x, (int)gameRes.y);
		
		g.setColor(new Color(255,255,255,64));
		g.fillRect(            4 *t+t/2,            0 +t/2,
				   ((int)dim.x-8)*t, (int)dim.y*t);
		
		drawGrid(g, gameRes, t);
		
		drawMetal(g, gameRes, t, circ);
		
		//powerstate
		for(int i=0; i<(int)dim.x; i++)
		 for(int j=0; j<(int)dim.y; j++){
			 if(simstate[1][i][j])
			 {	 g.setColor(new Color(0,0,0,127));
			 	g.fillRect( i*t  +t/2 + 1, j*t+t/2  +1, t-1,t-1);
			 }	
				
				
		}
		//</power>
		
	}
	public void drawGrid(Graphics g, pos2d gameRes,int t){
		g.setColor(new Color(0,0,0,64));
		for(int i=0; i<=(int)dim.x; i++){
			g.drawLine(i*t+t/2,            0 +t/2,
					   i*t+t/2, (int)dim.y*t +t/2);
		}
		for(int j=0; j<=(int)dim.y; j++){
			g.drawLine(         0  +t/2, j*t+t/2,
					   (int)dim.x*t+t/2, j*t +t/2);
		
		}
	}
	public void drawMetal(Graphics g, pos2d gameRes,int t, circuitDesign circ){
		g.setColor(new Color(0,0,0,127));
		for(int i=0; i<(int)dim.x; i++){
			for(int j=0; j<(int)dim.y; j++){
				if(circ.metal_layer[i][j])
					g.fillRect( i*t  +t/2 + 2, j*t+t/2  +2, t-3,t-3);
				
				if(circ.metal_H[i][j])
					g.fillRect( i*t  +t/2 + t-1, j*t+t/2  +2, 3,t-3);
				
				if(circ.metal_V[i][j])
					g.fillRect( i*t  +t/2 +2, j*t+t/2  +t-1, t-3, 3);
			}
		}
		g.setColor(new Color(255,255,255,128));
		for(int i=0; i<(int)dim.x; i++){
			for(int j=0; j<(int)dim.y; j++){
				if(circ.metal_layer[i][j])
					g.fillRect( i*t  +t/2 + 3, j*t+t/2  +3, t-5,t-5);
				
				if(circ.metal_H[i][j])
					g.fillRect( i*t  + t/2 +t-2, j*t+t/2  +3, 5,t-5);
				
				if(circ.metal_V[i][j])
					g.fillRect( i*t  +t/2 +3, j*t  +t/2 +t-2, t-5, 5);
			}
		}
		
	}
	
}
