
package figuras;
import figuras.FormaPintar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Dibujos extends JFrame {

  private final int UPDATE_RATE = 60;

  private int width;
  private int height;

  private PanelCanvas canvas;
  private JPanel mainPanel, toolsPanel;
  private JLabel lblMousePosition;
  private JButton btnUnDo, btnClear;
  private JComboBox<String> coBoxColor, coBoxShape;
  private JCheckBox chBoxIsFilled;

  private Stack<FormaPintar> shapes;

  private int iniciaX, iniciaY;
  private int terminaX, terminaY;

  private boolean isDrawing = false;

  public Dibujos(int width, int height) {
    this.width = width;
    this.height = height;
    this.canvas = new PanelCanvas();
    this.mainPanel = new JPanel(new BorderLayout());
    this.toolsPanel = new JPanel();
    this.lblMousePosition = new JLabel("0, 0");
    this.btnUnDo = new JButton("REBOBINAR");
    this.btnClear = new JButton("LIMPIAR");
    this.coBoxColor = new JComboBox<String>(new String[] { "ROJO", "VERDE", "AZUL", "CYAN", "MAGENTA", "AMARILLO","NEGRO","NARANJA","ROSA","GRIS OSCURO","GRIS","SUNRAY",
        "LAPIS LAZUL1","AZUL MARINO","AZUL CIELO","CELADON","VERDE CLARO","MYSTIC MAROON"});
    this.coBoxShape = new JComboBox<String>(new String[] { "LINEA", "RECTANGULO", "ELEPISE" });
    this.chBoxIsFilled = new JCheckBox("RELLENO");
    shapes = new Stack<FormaPintar>();

    addAttributes();
    addListeners();
    build();

    this.pack();
    this.setLocationRelativeTo(null);

    startLoop();
  }

  public void addAttributes() {
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
  }

  public void addListeners() {
    // Boton para rebobinar.
    btnUnDo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (shapes.size() > 0)
          shapes.pop();
      }
    });

    // Boton para limpiar.
    btnClear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (shapes.size() > 0)
          shapes.clear();
      }
    });

    // Canvas mouse coordenadas.
    canvas.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        iniciaX = e.getX();
        iniciaY = e.getY();

        isDrawing = true;
      }

      public void mouseReleased(MouseEvent e) {
        shapes.push(new FormaPintar(iniciaX, iniciaY, terminaX, terminaY, coBoxShape.getSelectedIndex(),
            chBoxIsFilled.isSelected(), FormaPintar.COLORS[coBoxColor.getSelectedIndex()]));

        isDrawing = false;
      }
    });

    canvas.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        lblMousePosition.setText(e.getX() + ", " + e.getY());
      }

      public void mouseDragged(MouseEvent e) {
        boolean lineSelected = coBoxShape.getSelectedIndex() == FormaPintar.LINEA;

        terminaX = (lineSelected) ? e.getX() : e.getX() - iniciaX;
        terminaY = (lineSelected) ? e.getY() : e.getY() - iniciaY;

        lblMousePosition.setText(iniciaX + ", " + iniciaY + "; " + terminaX + ", " + terminaY);
      }
    });
  }

  public void build() {
    this.toolsPanel.add(btnUnDo);
    this.toolsPanel.add(btnClear);
    this.toolsPanel.add(coBoxColor);
    this.toolsPanel.add(coBoxShape);
    this.toolsPanel.add(chBoxIsFilled);
    this.mainPanel.add(toolsPanel, BorderLayout.EAST);
    this.mainPanel.add(canvas, BorderLayout.CENTER);
    this.mainPanel.add(lblMousePosition, BorderLayout.NORTH );

    this.add(mainPanel);
  }

  public void startLoop() {
    Thread drawLoop = new Thread() {
      public void run() {
        while (true) {
          repaint();

          try {
            Thread.sleep(1000 / 60); // Desired frame rate.
          } catch (InterruptedException ex) {
          }
        }
      }
    }
            ;

    drawLoop.start();
  }

  class PanelCanvas extends JPanel {

    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      g.setColor(Color.decode("#F4F7F5"));
      g.fillRect(0, 0, width, height);

      shapes.stream().forEach(shape -> {
        g.setColor(shape.getColor());

        switch (shape.getTipo()) {
          case FormaPintar.RECTANGULO:
            if (shape.isFilled())
              g.fillRect(shape.iniciaX(), shape.iniciaY(), shape.terminaX(), shape.terminaY());
            else
              g.drawRect(shape.iniciaX(), shape.iniciaY(), shape.terminaX(), shape.terminaY());
            break;
          case FormaPintar.ELLIPSE:
            if (shape.isFilled())
              g.fillOval(shape.iniciaX(), shape.iniciaY(), shape.terminaX(), shape.terminaY());
            else
              g.drawOval(shape.iniciaX(), shape.iniciaY(), shape.terminaX(), shape.terminaY());
            break;
          case FormaPintar.LINEA:
            g.drawLine(shape.iniciaX(), shape.iniciaY(), shape.terminaX(), shape.terminaY());
            break;
        }

      });

      if (isDrawing) {
        g.setColor(Color.decode("#BBAADD"));
        switch (coBoxShape.getSelectedIndex()) {
          case FormaPintar.RECTANGULO:
            g.drawRect(iniciaX, iniciaY, terminaX, terminaY);
            break;
          case FormaPintar.ELLIPSE:
            g.drawOval(iniciaX, iniciaY, terminaX, terminaY);
            break;
          case FormaPintar.LINEA:
            g.drawLine(iniciaX, iniciaY, terminaX, terminaY);
            break;
        }
      }
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(width, height);
    }

  }

}