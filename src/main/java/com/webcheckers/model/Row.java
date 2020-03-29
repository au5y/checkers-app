package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An individual row of the board
 *
 * @author Zehra Amena Baig (zab1166)
 */

public class Row implements Iterable<Space>
{
  /**
   * The size of the row
   */
  private final static int DIMENSIONS = 8;
  /**
   * The ArrayList of spaces in the row
   */
  private ArrayList<Space> squares;
  /**
   * The index of the row
   */
  public final int index;

  /**
   * Constructor for row class
   *
   * @param index The index of the row
   * @param first used in initializeBoard method for piece arrangement
   */
  public Row(int index, boolean first)
  {
    this.index = index;
    this.squares = new ArrayList<Space>();
    initializeBoard(first);
  }

  /**
   * Initializes the board with alternating b&w spaces
   * And arranges the pieces for the start of the game
   */
  private void initializeBoard(boolean first)
  {
    boolean isBlackSpace;
    Piece red = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
    Piece white = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);

    if (index % 2 == 1)
    {
      isBlackSpace = true;
    } else
    {
      isBlackSpace = false;
    }

    for (int i = 0; i < DIMENSIONS; i++)
    {
      squares.add(new Space(i, isBlackSpace));
      isBlackSpace = !isBlackSpace;
    }

    placePieces(first, red, white);
  }

  /**
   * Helper method to place pieces on board
   *
   * @param first whether player is first or not (determines arrangement of piece colors)
   * @param red   red piece
   * @param white white piece
   */
  private void placePieces(boolean first, Piece red, Piece white)
  {
    if (first)
    {
      if (index == DIMENSIONS - 1 || index == DIMENSIONS - 2)
      {
        fillRow(red);
      } else if (index == 0 || index == 1)
      {
        fillRow(white);
      }
    } else
    {
      if (index == DIMENSIONS - 1 || index == DIMENSIONS - 2)
      {
        fillRow(white);
      } else if (index == 0 || index == 1)
      {
        fillRow(red);
      }
    }
  }

  /**
   * Places pieces in row in alternating fashion
   *
   * @param piece The piece to place on the squares
   */
  public void fillRow(Piece piece)
  {
    for (Space s : squares)
    {
      if (s.isValidSpace())
      {
        s.setPiece(piece);
      }
    }
  }

  /**
   * Returns the index of the row
   *
   * @return the row's index
   */
  public int getIndex()
  {
    return this.index;
  }

  public ArrayList<Space> getRow()
  {
    return squares;
  }

  /**
   * Helper function to find a cell at a given int.
   *
   * @param cellInt: the index for the cell or column
   * @return the Space requested.
   */
  public Space getSpaceAt(int cellInt)
  {
    return squares.get(cellInt);
  }

  @Override
  public Iterator<Space> iterator()
  {
    return squares.iterator();
  }
}
