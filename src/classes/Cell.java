package classes;


public class Cell {
	private String room, time;
	private int row, col;
	private boolean available;	
	
	public Cell() {
		room = "";
		time = "";
		row = -1;
		col = -1;
		available = false;
	}
	
	public Cell(String r, String t, int ro, int co, boolean av) {
		room = r;
		time = t;
		row = ro;
		col = co;
		available = av;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
	
	
}
