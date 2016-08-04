package engine.net.server;

public class AnalyzerThread extends Thread{

    @Override
    public void run(){
        long timeAnalysis = System.currentTimeMillis();//Время анализа MPS
        int timeAnalysisDelta = 1000;

        while (true){
            if (System.currentTimeMillis() > timeAnalysis+timeAnalysisDelta){//Анализ MPS
                timeAnalysis = System.currentTimeMillis();
                System.out.print("MPS: ");
                for (int i = 0; i < GameServer.peopleMax; i++){
                    System.out.print(GameServer.numberSend[i] + " | ");
                    GameServer.numberSend[i] = 0;
                }
                System.out.println();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }

    }
}
