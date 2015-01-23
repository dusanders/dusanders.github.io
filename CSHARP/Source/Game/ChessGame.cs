/*	Dustin Anderson
*	ChessGame.cs
*	This file is intended to show an implementation of the min/max algorithm using
*		the game of chess.
*/  

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GraphicChess
{
	public partial class ChessGame : Form
	{
		private const int boardSize = 8;
		private int boardSquareWidth = 40, boardSquareHeight = 40, boardHOffset = 30, boardVOffset = 30;//in pixels
		public struct Point
		{
			public int x;
			public int y;
			public Point(int xVal, int yVal) { x = xVal; y = yVal; }
			public static bool operator ==(Point p, Point q) { return p.x == q.x && p.y == q.y; }
			public static bool operator !=(Point p, Point q) { return !(p == q); }
			public override int GetHashCode()
			{
				return base.GetHashCode();
			}
			public override bool Equals(object obj)
			{
				return base.Equals(obj);
			}
		}

		public struct ChessMove
		{
			public Point origin;
			public Point destination;
			public ChessMove(ref Point theOrigin, ref Point theDestination) { origin = theOrigin; destination = theDestination; }
			public static bool operator ==(ChessMove m1, ChessMove m2) { return m1.origin == m2.origin && m1.destination == m2.destination; }
			public static bool operator !=(ChessMove m1, ChessMove m2) { return !(m1 == m2); }
			public override int GetHashCode()
			{
				return base.GetHashCode();
			}
			public override bool Equals(object obj)
			{
				return base.Equals(obj);
			}
		};

		public enum PIECE
		{
			EMPTY = 0, WPAWN = 1, WKNIGHT, WBISHOP, WROOK, WQUEEN, WKING,
			BPAWN, BKNIGHT, BBISHOP, BROOK, BQUEEN, BKING
		}
		private PIECE[,] board;
		private bool[] rook1Moved;
		private bool[] rook2Moved;
		private bool[] kingMoved;

		public enum PLAYER
		{
			INVALID = -1, WHITE, BLACK
		}

		private double[] values;
		private const int MaxMoves = 200;
		private PLAYER whoseTurn;
		private List<ChessMove> moveHistory;
		private int moveIndex;

		private delegate int getMovesByPiece(Point origin, ChessMove[] moves, int start);
		public ChessGame()
		{
			InitializeComponent();
			this.Click += form_Click;

			board = new PIECE[boardSize, boardSize];
			rook1Moved = new bool[2];
			rook2Moved = new bool[2];
			kingMoved = new bool[2];
			values = new double[13];

			setupBoard();

			values[0] = 0;
			values[(int)PIECE.WPAWN] = 1;
			values[(int)PIECE.WKNIGHT] = 3;
			values[(int)PIECE.WBISHOP] = 3.1;
			values[(int)PIECE.WROOK] = 5;
			values[(int)PIECE.WQUEEN] = 9;
			values[(int)PIECE.WKING] = 1000;//Effectively infinite

			for (int i = (int)PIECE.BPAWN; i <= (int)PIECE.BKING; i++) values[i] = -values[i - (int)PIECE.WKING];
			moveHistory = new List<ChessMove>();
			moveIndex = 0;
			undoMoveToolStripMenuItem.Enabled = redoMoveToolStripMenuItem.Enabled = false;
			drawBoard();
		}

		private void form_Click(object sender, EventArgs e)
		{
			MouseEventArgs me = (MouseEventArgs)e;
			int x = (me.X - boardHOffset) / boardSquareWidth;
			int y = boardSize - 1 - (me.Y - boardVOffset) / boardSquareHeight;
			if (!isOnBoard(new Point(x, y))) return;
			if (me.Button == System.Windows.Forms.MouseButtons.Left)
			{
				textBox2.Text = x + "," + y;
			}
			else
			{
				textBox1.Text = x + "," + y;
			}
			drawBoard();
		}

		private void setupBoard()
		{
			int i, j;

			for (i = 0; i < boardSize; i++)
			{
				for (j = 0; j < boardSize; j++)
				{
					board[i, j] = PIECE.EMPTY;
				}
			}

			for (i = 0; i < boardSize; i++)
			{
				board[i, 1] = PIECE.WPAWN;
			}
			board[0, 0] = board[7, 0] = PIECE.WROOK;
			board[1, 0] = board[6, 0] = PIECE.WKNIGHT;
			board[2, 0] = board[5, 0] = PIECE.WBISHOP;
			board[3, 0] = PIECE.WQUEEN;
			board[4, 0] = PIECE.WKING;

			for (i = 0; i < boardSize; i++)
			{
				for (j = 6; j < boardSize; j++)
				{
					board[i, j] = board[i, 7 - j] + (int)PIECE.WKING;
				}
			}

			for (i = 0; i < 2; i++)
			{
				rook1Moved[i] = false;
				rook2Moved[i] = false;
				kingMoved[i] = false;
			}
			whoseTurn = PLAYER.WHITE;
		}

		private void playChess()
		{
			drawBoard();
		}

		private PLAYER opponent(PLAYER player)
		{
			if (player == PLAYER.WHITE) return PLAYER.BLACK;
			return PLAYER.WHITE;
		}

		public bool makeMove(ChessMove move)
		{
			if (!isLegalMove(move)) return false;

			PIECE piece = board[move.origin.x, move.origin.y];
			board[move.destination.x, move.destination.y] = piece;
			board[move.origin.x, move.origin.y] = 0;

			whoseTurn = opponent(whoseTurn);
			return true;
		}

		public bool isLegalMove(ChessMove move)
		{
			if (!isOnBoard(move.origin)) return false;
			if (!isOnBoard(move.destination)) return false;

			ChessMove[] moves = new ChessMove[MaxMoves];
			int numMoves = getLegalMoves(moves);

			for (int i = 0; i < numMoves; i++)
			{
				if (move == moves[i]) return true;
			}

			return false;
		}

		private PLAYER whosePiece(PIECE piece)
		{
			if (piece < PIECE.WPAWN) return PLAYER.INVALID;
			if (piece < PIECE.BPAWN) return PLAYER.WHITE;
			if (piece <= PIECE.BKING) return PLAYER.BLACK;
			return PLAYER.INVALID;
		}

		private void drawBoard()
		{
			System.Drawing.Graphics graphics = CreateGraphics();

			int i, j;
			PIECE piece;
			Rectangle boardRect = new Rectangle(boardHOffset, boardVOffset, boardSize * boardSquareWidth, boardSize * boardSquareHeight);
			Rectangle squareRect, innerRect;
			graphics.Clip = new Region(boardRect);

			System.Drawing.Bitmap[] images = {bitmaps.wpawn, bitmaps.wknight, bitmaps.wbishop, bitmaps.wrook,
                                             bitmaps.wqueen, bitmaps.wking, bitmaps.bpawn, bitmaps.bknight,
                                             bitmaps.bbishop, bitmaps.brook, bitmaps.bqueen, bitmaps.bking};

			Pen greenPen = new Pen(new SolidBrush(Color.Green), 5), redPen = new Pen(new SolidBrush(Color.Red), 5);
			SolidBrush whiteBrush = new SolidBrush(Color.White), blackBrush = new SolidBrush(Color.Black);
			graphics.FillRectangle(whiteBrush, boardRect);

			for (i = 0; i <= boardSize; i++)
			{
				graphics.DrawLine(System.Drawing.Pens.Black, i * boardSquareWidth + boardHOffset, boardVOffset, i * boardSquareWidth + boardHOffset, boardVOffset + boardSquareHeight * boardSize);
				graphics.DrawLine(System.Drawing.Pens.Black, boardHOffset, i * boardSquareHeight + boardVOffset, boardHOffset + boardSquareWidth * boardSize, i * boardSquareHeight + boardVOffset);
			}

			Rectangle Rect;

			for (j = 0; j < boardSize; j++)
			{
				for (i = 0; i < boardSize; i++)
				{
					Rect = new Rectangle(i * boardSquareWidth + boardHOffset, (boardSize - 1 - j) * boardSquareHeight + boardVOffset, boardSquareWidth, boardSquareHeight);
					squareRect = Rect;
					squareRect.Y = squareRect.Bottom - boardSquareHeight;
					innerRect = squareRect;
					innerRect.Y += boardSquareHeight / 5;
					innerRect.Height -= boardSquareHeight / 5;
					innerRect.X += boardSquareWidth / 5;
					innerRect.Width -= boardSquareWidth / 5;

					if ((i + j) % 2 == 0)
					{
						graphics.FillRectangle(blackBrush, squareRect);
					}

					piece = board[i, j];
					if (piece != PIECE.EMPTY)
					{
						graphics.DrawImage(images[(int)piece - 1], innerRect);
					}
				}
			}

			Point from, to;
			getFromTo(out from, out to);
			if (isOnBoard(from)) graphics.DrawRectangle(greenPen, getSquareRect(from));
			if (isOnBoard(to)) graphics.DrawRectangle(redPen, getSquareRect(to));
		}

		private Rectangle getSquareRect(Point p)
		{
			Rectangle squareRect = new Rectangle(p.x * boardSquareWidth + boardHOffset,
				(boardSize - 1 - p.y) * boardSquareHeight + boardVOffset, boardSquareWidth, boardSquareHeight);
			return squareRect;
		}

		private bool isOnBoard(Point p)
		{
			if (p.x >= 0 && p.y >= 0 && p.x < 8 && p.y < 8) return true;
			return false;
		}

		private bool inCheck()
		{
			bool retValue = false;

			return retValue;
		}

		private bool inCheckmate()
		{
			return false;
		}

		private int getLegalMoves(ChessMove[] moves)
		{
			int numMoves = 0;
			getMovesByPiece[] getParticularMoves =
            {
                getPawnMoves,
                getKnightMoves,
                getBishopMoves,
                getRookMoves,
                getQueenMoves,
                getKingMoves
            };

			for (int i = 0; i < boardSize; i++)
			{
				for (int j = 0; j < boardSize; j++)
				{
					if (whosePiece(board[i, j]) == whoseTurn)
					{
						Point origin = new Point(i, j);
						PIECE piece = board[i, j];
						if (piece > PIECE.WKING) piece -= PIECE.WKING;
						numMoves += getParticularMoves[(int)piece - 1](origin, moves, numMoves);
					}
				}
			}

			return numMoves;
		}

		private int getPawnMoves(Point origin, ChessMove[] moves, int start)
		{
			int numMoves = 0;
			PIECE piece = board[origin.x, origin.y];
			PLAYER opponent;
			int direction, baseRank, farEnd, curMove = start;
			PLAYER player = whosePiece(piece);

			if (player == PLAYER.WHITE)
			{
				opponent = PLAYER.BLACK;
				direction = 1;
				baseRank = 1;
				farEnd = boardSize - 1;
			}
			else
			{
				opponent = PLAYER.WHITE;
				direction = -1;
				baseRank = boardSize - 2;
				farEnd = 0;
			}

			if (origin.y != farEnd && board[origin.x, origin.y + direction] == 0)
			{
				moves[curMove].origin = origin;
				moves[curMove].destination.x = origin.x;
				moves[curMove].destination.y = origin.y + direction;
				curMove++;
				numMoves++;
			}

			if (origin.y == baseRank && board[origin.x, origin.y + direction] == 0 &&
				board[origin.x, origin.y + 2 * direction] == 0)
			{
				moves[curMove].origin = origin;
				moves[curMove].destination.x = origin.x;
				moves[curMove].destination.y = origin.y + 2 * direction;
				curMove++;
				numMoves++;
			}

			if (origin.y != farEnd && origin.x > 0 &&
				whosePiece(board[origin.x - 1, origin.y + direction]) == opponent)
			{
				//Capture an enemy piece.
				moves[curMove].origin = origin;
				moves[curMove].destination.x = origin.x - 1;
				moves[curMove].destination.y = origin.y + direction;
				curMove++;
				numMoves++;
			}

			if (origin.y != farEnd && origin.x < boardSize - 1 &&
				whosePiece(board[origin.x + 1, origin.y + direction]) == opponent)
			{
				//Capture an enemy piece.
				moves[curMove].origin = origin;
				moves[curMove].destination.x = origin.x + 1;
				moves[curMove].destination.y = origin.y + direction;
				curMove++;
				numMoves++;
			}

			return numMoves;
		}

		private int getKnightMoves(Point origin, ChessMove[] moves, int start)
		{
			int numMoves = 0;
			int dx, dy;
			Point destination;
			PLAYER player = whosePiece(board[origin.x, origin.y]);
			int curMove = start;

			for (dx = -2; dx <= 2; dx++)
			{
				for (dy = -2; dy <= 2; dy++)
				{
					//Knights can move 2 squares in one direction and 1 square in the other.
					if (Math.Abs(dx) + Math.Abs(dy) != 3) continue;

					destination.x = origin.x + dx;
					destination.y = origin.y + dy;
					if (!isOnBoard(destination)) continue;

					if (whosePiece(board[destination.x, destination.y]) != player)
					{
						moves[curMove].origin = origin;
						moves[curMove].destination = destination;
						curMove++;
						numMoves++;
					}
				}
			}

			return numMoves;
		}

		private int getBishopMoves(Point origin, ChessMove[] moves, int start)
		{
			int numMoves = 0, curMove = start;
			int i, xDirection, yDirection;
			Point destination;
			PLAYER player = whosePiece(board[origin.x, origin.y]);

			for (xDirection = -1; xDirection <= 1; xDirection += 2)
			{
				for (yDirection = -1; yDirection <= 1; yDirection += 2)
				{
					for (i = 1; i < boardSize; i++)
					{
						destination.x = origin.x + i * xDirection;
						destination.y = origin.y + i * yDirection;
						if (!isOnBoard(destination)) break;

						if (whosePiece(board[destination.x, destination.y]) != player)
						{
							moves[curMove].origin = origin;
							moves[curMove].destination = destination;
							curMove++;
							numMoves++;
						}
						//A bishop cannot move through other pieces.
						if (board[destination.x, destination.y] != 0) break;
					}
				}
			}

			return numMoves;
		}

		private int getRookMoves(Point origin, ChessMove[] moves, int start)
		{
			int numMoves = 0, curMove = start;
			int i, xDirection, yDirection;
			Point destination;
			PLAYER player = whosePiece(board[origin.x, origin.y]);

			for (xDirection = -1; xDirection <= 1; xDirection++)
			{
				for (yDirection = -1; yDirection <= 1; yDirection++)
				{
					if (Math.Abs(xDirection) + Math.Abs(yDirection) > 1) continue;

					for (i = 1; i < boardSize; i++)
					{
						destination.x = origin.x + i * xDirection;
						destination.y = origin.y + i * yDirection;
						if (!isOnBoard(destination)) break;

						if (whosePiece(board[destination.x, destination.y]) != player)
						{
							moves[curMove].origin = origin;
							moves[curMove].destination = destination;
							curMove++;
							numMoves++;
						}
						//A rook cannot move through other pieces.
						if (board[destination.x, destination.y] != 0) break;
					}
				}
			}

			return numMoves;
		}

		private int getQueenMoves(Point origin, ChessMove[] moves, int start)
		{
			int numMoves = 0;
			int curMove = start;
			int i, xDirection, yDirection;
			Point destination;
			PLAYER player = whosePiece(board[origin.x, origin.y]);
			//get legal moves for rook moves
			for (xDirection = -1; xDirection <= 1; xDirection++)
			{
				for (yDirection = -1; yDirection <= 1; yDirection++)
				{
					if (Math.Abs(xDirection) + Math.Abs(yDirection) > 1) continue;

					for (i = 1; i < boardSize; i++)
					{
						destination.x = origin.x + i * xDirection;
						destination.y = origin.y + i * yDirection;
						if (!isOnBoard(destination)) break;

						if (whosePiece(board[destination.x, destination.y]) != player)
						{
							moves[curMove].origin = origin;
							moves[curMove].destination = destination;
							curMove++;
							numMoves++;
						}

						if (board[destination.x, destination.y] != 0) break;
					}
				}
			}
			//get legal moves for bishop moves
			for (xDirection = -1; xDirection <= 1; xDirection += 2)
			{
				for (yDirection = -1; yDirection <= 1; yDirection += 2)
				{
					for (i = 1; i < boardSize; i++)
					{
						destination.x = origin.x + i * xDirection;
						destination.y = origin.y + i * yDirection;
						if (!isOnBoard(destination)) break;

						if (whosePiece(board[destination.x, destination.y]) != player)
						{
							moves[curMove].origin = origin;
							moves[curMove].destination = destination;
							curMove++;
							numMoves++;
						}
						//A bishop cannot move through other pieces.
						if (board[destination.x, destination.y] != 0) break;
					}
				}
			}
			return numMoves;
		}

		private int getKingMoves(Point origin, ChessMove[] moves, int start)
		{
			int numMoves = 0, curMove = start;
			int xDirection, yDirection;
			Point destination;
			PLAYER player = whosePiece(board[origin.x, origin.y]);

			for (xDirection = -1; xDirection <= 1; xDirection++)
			{
				for (yDirection = -1; yDirection <= 1; yDirection++)
				{
					destination.x = origin.x + xDirection;
					destination.y = origin.y + yDirection;
					if (!isOnBoard(destination)) continue;

					if (whosePiece(board[destination.x, destination.y]) != player)
					{
						moves[curMove].origin = origin;
						moves[curMove].destination = destination;
						curMove++;
						numMoves++;
					}
				}
			}

			return numMoves;
		}

		private ChessMove bestMove(int depth, ref int totalMoves)
		{
			ChessMove niceMove = new ChessMove();
			niceMove.origin.x = -1;
			ChessMove[] moves = new ChessMove[MaxMoves];
			totalMoves = 0;
			int numMoves = getLegalMoves(moves);
			if (numMoves == 0) return niceMove;
			double[] nextMoves = new double[numMoves];
			for (int i = 0; i < numMoves; i++)
			{
				PIECE former = board[moves[i].destination.x, moves[i].destination.y];
				makeMove(moves[i]);
				//recursively check this move
				nextMoves[i] = evaluatePosition(depth-1, ref totalMoves);
				//undo the move
				undoMove(moves[i], former);
			}
			int bestFound = 0;
			//ensure no 'draw by repetition'
			bool duplicateMove = true;
			while ((whoseTurn == PLAYER.WHITE) && (duplicateMove))
			{
				bestFound = findMax(nextMoves);
				niceMove = moves[bestFound];
				if((moveHistory.Count > 3) && (niceMove != moveHistory[moveHistory.Count - 4]))
				{
					duplicateMove = false;
				}
				//check for repetition
				if ((moveHistory.Count > 3) && (niceMove == moveHistory[moveHistory.Count - 4]))
				{	//modify this move to infinity
					nextMoves[bestFound] = -1000;
				}
				if (moveHistory.Count <= 3)
				{
					duplicateMove = false;
				}
			}
			while ((whoseTurn == PLAYER.BLACK) && (duplicateMove))
			{
				bestFound = findMin(nextMoves);
				niceMove = moves[bestFound];
				if((moveHistory.Count > 3) && (niceMove != moveHistory[moveHistory.Count - 4]))
				{
					duplicateMove = false;
				}
				//check for repetition
				if ((moveHistory.Count > 3) && (niceMove == moveHistory[moveHistory.Count - 4]))
				{	//modify this move to infinity
					nextMoves[bestFound] = 1000;
				}
				if(moveHistory.Count <= 3)
				{
					duplicateMove = false;
				}
			}
			return niceMove;
		}

		private int findMin(double[] inValues)
		{
			double foundMin = inValues[0];
			int foundIndex = 0;
			for (int i = 1; i < inValues.Length; i++)
			{
				if (inValues[i] < foundMin)
				{
					foundMin = inValues[i];
					foundIndex = i;
				}
			}
			return foundIndex;
		}
		private int findMax(double[] inValues)
		{
			double foundMin = inValues[0];
			int foundIndex = 0;
			for (int i = 1; i < inValues.Length; i++)
			{
				if (inValues[i] > foundMin)
				{
					foundMin = inValues[i];
					foundIndex = i;
				}
			}
			return foundIndex;
		}

		private double evaluatePosition(int depth, ref int totalMoves)
		{
			ChessMove[] possibleMoves = new ChessMove[MaxMoves];
			int numMoves = getLegalMoves(possibleMoves);
			double[] movesValues = new double[numMoves];
			if (depth == 0)
			{
				return getBoardCount();
			}
			depth--;
			for (int i = 0; i < numMoves; i++)
			{
				totalMoves++; 
				PIECE former = board[possibleMoves[i].destination.x, possibleMoves[i].destination.y];
				makeMove(possibleMoves[i]);
				movesValues[i] = evaluatePosition(depth, ref totalMoves);
				undoMove(possibleMoves[i], former);
			}
			if (whoseTurn == PLAYER.WHITE)
			{	//white gets the maximum
				return movesValues[findMax(movesValues)];
			}
			else
			{	//black gets the minimum
				return movesValues[findMin(movesValues)];
			}
		}
		private double getBoardCount()
		{
			double count = 0;
			for (int i = 0; i < boardSize; i++)
			{
				for (int j = 0; j < boardSize; j++)
				{
					count += values[(int)board[i, j]];
				}
			}
			return count;
		}

		private void undoMove(ChessMove m, PIECE formerOccupant)
		{
			board[m.origin.x, m.origin.y] = board[m.destination.x, m.destination.y];
			board[m.destination.x, m.destination.y] = formerOccupant;
			whoseTurn = opponent(whoseTurn);
		}

		private void buttonSubmit_Click(object sender, EventArgs e)
		{
			if (radioButtonResign.Checked)
			{
				txtMessage.Text = "I accept your resignation. Well played.";
				btnSubmit.Enabled = false;
				return;
			}
			if (radioButtonUserDecides.Checked)
			{
				Point from, to;
				if (!getFromTo(out from, out to, true)) return;
				ChessMove theMove = new ChessMove(ref from, ref to);
				if (!makeMove(theMove))
				{
					txtMessage.Text = "Sorry, that move is illegal. Please try again.";
					return;
				}
				moveHistory.Add(theMove); moveIndex++; undoMoveToolStripMenuItem.Enabled = true;
				drawBoard();
				if (inCheckmate())
				{
					txtMessage.Text = "I am checkmated! Nice game.";
					btnSubmit.Enabled = false;
					return;
				}
				if (inCheck())
				{
					txtMessage.Text = "I am in check!";
				}
			}
			int totalMoves = 0;
			ChessMove toMake = bestMove(3, ref totalMoves);
			makeMove(toMake);
			txtMessage.Text = "After considering " + totalMoves + " moves, I have decided to move from " + toMake.origin.x + "," + toMake.origin.y + " to " + toMake.destination.x + "," + toMake.destination.y + ".";
			moveHistory.Add(toMake); moveIndex++; undoMoveToolStripMenuItem.Enabled = true;
			drawBoard();
		}

		private bool getFromTo(out Point from, out Point to, bool report = false)
		{
			bool retValue = true;
			try
			{
				string[] fromStr = textBox2.Text.Split(',');
				from = new Point(Int32.Parse(fromStr[0]), Int32.Parse(fromStr[1]));
				string[] toStr = textBox1.Text.Split(',');
				to = new Point(Int32.Parse(toStr[0]), Int32.Parse(toStr[1]));
			}
			catch (Exception)
			{
				retValue = false;
				from = to = new Point();
				if (report) txtMessage.Text = "Sorry, that move is not in the correct format. Please try again.";
			}
			return retValue;
		}

		private void radioButton3_CheckedChanged(object sender, EventArgs e)
		{

		}

		private void radioButton2_CheckedChanged(object sender, EventArgs e)
		{

		}

		private void radioButton1_CheckedChanged(object sender, EventArgs e)
		{

		}

		private void textBox2_TextChanged(object sender, EventArgs e)
		{

		}

		private void textBox1_TextChanged(object sender, EventArgs e)
		{

		}

		private void label2_Click(object sender, EventArgs e)
		{

		}

		private void label1_Click(object sender, EventArgs e)
		{

		}

		private void undoMoveToolStripMenuItem_Click(object sender, EventArgs e)
		{
			moveIndex--;
			recapitulateGame();
			drawBoard();
			redoMoveToolStripMenuItem.Enabled = true;
			if (moveIndex == 0)
			{
				undoMoveToolStripMenuItem.Enabled = false;
			}
		}

		private void redoMoveToolStripMenuItem_Click(object sender, EventArgs e)
		{
			moveIndex++;
			recapitulateGame();
			drawBoard();
			undoMoveToolStripMenuItem.Enabled = true;
			if (moveIndex == moveHistory.Count)
			{
				redoMoveToolStripMenuItem.Enabled = false;
			}
		}

		private void recapitulateGame()
		{
			setupBoard();
			for (int i = 0; i < moveIndex; i++)
			{
				makeMove(moveHistory[i]);
			}
		}

		private void ChessGame_Load(object sender, EventArgs e)
		{
			drawBoard();
		}

		private void panelChessboard_Paint()
		{

		}
	}
}
