import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class BlockChain {

    private  List<Block> blockChain = Collections.synchronizedList(new ArrayList<Block>());
    private int difficultyMineBlock;

    public BlockChain(int difficultyMineBlock) {
        this.difficultyMineBlock = difficultyMineBlock;
    }

    public void addBlock(Block block) {
        block.mineBlock(difficultyMineBlock);
        blockChain.add(block);
        System.out.println("Block '"+block.getData()+"' added!");
    }

    public String getPreviousBlockHash() {
        return blockChain.size() == 0 ? null : blockChain.get(blockChain.size() - 1).getHash();
    }

    public List<Block> getBlockList() {
        return blockChain;
    }
}
