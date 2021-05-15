package Fourmis;

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
            jlb.setBackground(Color.DARK_GRAY);
        }
        this.setContentPane( grid );

        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.pack();
        this.setVisible( true );

    }

    public void update( BitSet[][] cells ){

        Container grid = this.getContentPane();
        int k = 0;
        for(int i= 0; i < height; i++)
            for( int j = 0; j < width; j++) {
                if ( cells[i][j].get( 0 ) )
                    grid.getComponent( k ).setBackground( Color.WHITE );
                else if ( cells[i][j].get( 1 ) )
                    grid.getComponent( k ).setBackground( Color.BLACK );
                else {
                    int r = Color.DARK_GRAY.getRed(), g = r, b = r;
                    if ( cells[i][j].get( 2 ) ) {
                        r = 100;
                        g = 0;
                        b = 0;
                    }
                    if ( cells[i][j].get( 3 ) )
                        r = 150;
                    if ( cells[i][j].get( 4 ) )
                        r = 200;
                    if ( cells[i][j].get( 5 ) )
                        g = 255;
                    if ( cells[i][j].get( 6 ) )
                        b = 255;
                    grid.getComponent( k ).setBackground( new Color( r, g, b ) );
                }
                k++;
            }

    }
}
