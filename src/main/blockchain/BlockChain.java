import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockChain {

    private  List<Block> blockChain = Collections.synchronizedList(new ArrayList<Block>());
    private int difficultyMineBlock;

    public BlockChain(int difficultyMineBlock) {
        this.difficultyMineBlock = difficultyMineBlock;
    }

    public void addBlock(Block block, Miner miner) {
        block.proofOFwork(difficultyMineBlock);
        blockChain.add(block);
        System.out.println(String.format(TextConstants.MINER_ADICIONOU_O_BLOCK, miner.getName(),block.getData()));
    }

    public String getPreviousBlockHash() {
        return blockChain.get(blockChain.size() - 1).getHash();
    }

    public List<Block> getBlockList() {
        return blockChain;
    }

    public void addGenesisBlock() {
        blockChain.add(new Block("\"Starts from here!\"","0"));
    }
}
