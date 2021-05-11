import javax.swing.*;
import java.awt.*;
import java.util.BitSet;

public class Display extends JFrame {

    private int cellSize;
    private int width;
    private int height;

    public Display(int width, int height, int cellSize){
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;

        JPanel grid = new JPanel(new GridLayout(height, width, 1, 1));
        grid.setPreferredSize(new Dimension(width * cellSize, height * cellSize));
        for (int i = 0; i < height * width; i++) {
            JLabel jlb = new JLabel();
            grid.add(jlb);
            jlb.setOpaque(true);
            jlb.setBackground(Color.WHITE);
        }
        this.setContentPane( grid );

        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.pack();
        this.setVisible( true );

    }

    public void update( BitSet[][] cells ){

        Container grid = this.getContentPane();

        int k = 0; // indice du JLabel dans la grille

        for(int i= 0; i < width; i++)
            for( int j = 0; j < height; j++) {

                if ( cells[i][j].get( 0 ) ) // fourmilière
                    ( (JLabel) grid.getComponent( k ) ).setText( "F" );

                else if ( cells[i][j].get( 1 ) ) // obstacle
                    ( (JLabel) grid.getComponent( k ) ).setText( "O" );

                else if ( cells[i][j].get( 5 ) ) // nourriture
                    ( (JLabel) grid.getComponent( k ) ).setText( "N" );

                else {
                    int r = 255, g = 255, b = 255;
                    if ( cells[i][j].get( 2 ) ) // soldat
                        b = 0; // jaune
                    if ( cells[i][j].get( 3 ) ) // ouvrier aller
                        g = 0; // magenta
                    if ( cells[i][j].get( 4 ) ) // ouvrier retour
                        r -= 128; // (cyan avec phéromones)
                    if ( cells[i][j].get( 6 ) ) // phéromones
                        r -= 127;
                    grid.getComponent( k ).setBackground( new Color( r, g, b ) );
                }
                k++;
            }

    }
}
