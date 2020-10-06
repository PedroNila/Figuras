/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figuras;
import java.awt.Color;
public class FormaPintar {
public static final int LINEA = 0;
public static final int RECTANGULO = 1;
public static final int ELLIPSE = 2;


  public static final Color[] COLORS = new Color[] { Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA,
      Color.YELLOW, Color.BLACK,Color.ORANGE, Color.pink, Color.darkGray,Color.gray, Color.decode("#EDAE49"),Color.decode("#00798C"),Color.decode("#726DA8"),
      Color.decode("#A0D2DB"),Color.decode("#A6D49F"),Color.decode("#2AFC98"),Color.decode("#AF3B6E")};

  private int tipo;
  private boolean isFilled;
  private Color color;

  private int iniciaX, iniciaY;
  private int terminaX, terminaY;

  public FormaPintar(int iniciaX, int iniciaY, int terminaX, int terminaY, int tipo, boolean isFilled, Color color) {

    this.tipo = tipo;
    this.isFilled = isFilled;
    this.color = color;
    this.iniciaX = iniciaX;
    this.iniciaY = iniciaY;
    this.terminaX = terminaX;
    this.terminaY = terminaY;

  }

  public int getTipo() {
    return this.tipo;
  }

  public boolean isFilled() {
    return this.isFilled;
  }

  public Color getColor() {
    return this.color;
  }

  public int iniciaX() {
    return this.iniciaX;
  }

  public int iniciaY() {
    return this.iniciaY;
  }

  public int terminaX() {
    return this.terminaX;
  }

  public int terminaY() {
    return this.terminaY;
  }

}