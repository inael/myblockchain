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
        int difficulty = blockChain.getDifficultyMineBlock();
        for (int i = 0; i < blockNumber; i++) {

            String blockName = name + "-" + UUID.randomUUID().toString().substring(0, 4);
            Block lastBlock = blockChain.getLastBlock();

            Block newBlock = new Block(blockName, lastBlock.getHash());
            System.out.println(String.format(TextConstants.START_MINING, name));
            newBlock.mineBlock(difficulty);
            System.out.println(String.format(TextConstants.END_MINING, name));
            blockChain.addBlock(newBlock, this);

        }
    }

}
