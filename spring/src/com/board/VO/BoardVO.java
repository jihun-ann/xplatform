package com.board.VO;

public class BoardVO
{
  private int seq;
  private String name;
  private String title;
  private String content;
  private String pass;
  private int hit;
  private String regdate;
  private String replyseq;
  
  
  
  
  
  
  
  @Override
public String toString() {
	return "BoardVO [seq=" + seq + ", name=" + name + ", title=" + title
			+ ", content=" + content + ", pass=" + pass + ", hit=" + hit
			+ ", regdate=" + regdate + ", replyseq=" + replyseq + "]";
}

public String getReplyseq() {
	return replyseq;
}

public void setReplyseq(String replyseq) {
	this.replyseq = replyseq;
}

public int getSeq()
  {
    return this.seq;
  }
  

public void setSeq(int seq)
  {
    this.seq = seq;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public String getPass()
  {
    return this.pass;
  }
  
  public void setPass(String pass)
  {
    this.pass = pass;
  }
  
  public int getHit()
  {
    return this.hit;
  }
  
  public void setHit(int hit)
  {
    this.hit = hit;
  }
  
  public String getRegdate()
  {
    return this.regdate;
  }
  
  public void setRegdate(String regdate)
  {
    this.regdate = regdate;
  }
  
}
