import lombok.Getter;

import java.util.UUID;

@Getter
public class Miner implements Runnable {

    String name;
    BlockChain blockChain;
    Integer blockNumber;

    public Miner(int id, BlockChain blockChain, Integer blockNumber) {
        this.name = new Integer(id).toString();
        this.blockChain = blockChain;
        this.blockNumber = blockNumber;
    }

    @Override
    public void run() {
        for (int i = 0; i < blockNumber; i++) {
            String blockName = name +"-" + UUID.randomUUID().toString().substring(0,4);
            Block newBlock = new Block(blockName, blockChain.getPreviousBlockHash());
            newBlock.mineBlock(blockChain.getDifficultyMineBlock());
            blockChain.addBlock(newBlock,this);
        }
    }

}
