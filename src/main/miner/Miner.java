import lombok.Getter;

import java.util.Random;

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
            String blockName = name +"-" +new Random().nextInt();
            Block block = new Block(blockName, blockChain.getPreviousBlockHash());

            System.out.println(String.format(TextConstants.MINER_ADDING_NEW_BLOCK_S,name, block.getData()));

            blockChain.addBlock(block,this);
        }
    }

}
