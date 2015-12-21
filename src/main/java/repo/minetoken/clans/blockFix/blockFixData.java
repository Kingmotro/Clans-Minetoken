package repo.minetoken.clans.blockFix;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import repo.minetoken.clans.Clans;

public class blockFixData
{
  protected Block block;
  protected int fromID;
  protected byte fromData;
  protected int toID;
  protected byte toData;
  protected long expireDelay;
  protected long epoch;
  protected long meltDelay = 0L;
  protected long meltLast = 0L;
  
  @SuppressWarnings("deprecation")
public blockFixData(Block block, int toID, byte toData, long expireDelay, long meltDelay)
  {
    this.block = block;
    
    fromID = block.getTypeId();
    fromData = block.getData();
    
    this.toID = toID;
    this.toData = toData;
    
    this.expireDelay = expireDelay;
    epoch = System.currentTimeMillis();
    
    this. meltDelay = meltDelay;
    meltLast = System.currentTimeMillis();
    
	set();
  }
  
  public boolean expire()
  {
    if (System.currentTimeMillis() - epoch < expireDelay) {
      return false;
    }
    if (melt()) {
      return false;
    }
    restore();
    return true;
  }
  
  @SuppressWarnings("deprecation")
public boolean melt()
  {
	
    if ((block.getTypeId() != 78) && (block.getTypeId() != 80)) {
      return false;
    }
    if ((block.getRelative(BlockFace.UP).getTypeId() == 78) || (block.getRelative(BlockFace.UP).getTypeId() == 80))
    {
      meltLast = System.currentTimeMillis();
      return true;
    }
    if (System.currentTimeMillis() - meltLast < meltDelay) {
    	
      return true;
    }
    if (block.getTypeId() == 80) {
      block.setTypeIdAndData(78, (byte)7, false);
    }
    byte data = block.getData();
    if (data <= 0) {
      return false;
    }
    block.setData((byte)(block.getData() - 1));
    meltLast = System.currentTimeMillis();
    return true;
  }
  
  public void update(int toIDIn, byte toDataIn)
  {
    toID = toIDIn;
    toData = toDataIn;
    
    set();
  }
  
  public void update(int toID, byte addData, long expireTime)
  {
    if (toID == 78) {
      if (toID == 78) {
        toData = ((byte)Math.min(7, toData + addData));
      } else {
        toData = addData;
      }
    }
    this.toID = toID;
    
    set();
    
    expireDelay = expireTime;
    epoch = System.currentTimeMillis();
  }
  
  public void update(int toID, byte addData, long expireTime, long meltDelay)
  {
    if (toID == 78) {
      if (toID == 78) {
        toData = ((byte)Math.min(7, toData + addData));
      } else {
        toData = addData;
      }
    }
    this.toID = toID;
    
    set();
    
    expireDelay = expireTime;
    epoch = System.currentTimeMillis();
    if (meltDelay < meltDelay) {
      meltDelay = ((meltDelay + meltDelay) / 2L);
    }
  }
  
  @SuppressWarnings("deprecation")
public void set()
  {
    if ((toID == 78) && (toData == 7)) {
      block.setTypeIdAndData(80, (byte)0, true);
    } else {
      block.setTypeIdAndData(toID, toData, true);
    }
  }
  
  @SuppressWarnings("deprecation")
public void restore()
  {
    block.setTypeIdAndData(fromID, fromData, true);
  }
  
  public void setFromId(int i)
  {
    fromID = i;
  }
  
  public void setFromData(byte i)
  {
    fromData = i;
  }
}