package com.todo.thomas.scrolltask.myprovider;

public class Task {
	private int    id;
	private String name;
	private int    level;
	private String detail;
	private int    top;
    private int    priority;

    public Task(int id, String name, int level, String detail, int top, int priority) {
		this.id       = id;
		this.name     = name;
		this.level    = level;
		this.detail   = detail;
		this.top      = top;
        this.priority = priority;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
