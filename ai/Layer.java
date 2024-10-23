package ai;

public class Layer {
    int numInNodes;
    int numOutNodes;
    double[][] weights;
    double[] biases;

    public Layer(int numInNodes, int numOutNodes) {
        this.numInNodes = numInNodes;
        this.numOutNodes = numOutNodes;
        weights = new double[numInNodes][numOutNodes];
        biases = new double[numOutNodes];
        randFill();
    }

    public Layer(Layer other) {
        this.numInNodes = other.numInNodes;
        this.numOutNodes = other.numOutNodes;

        this.weights = new double[numInNodes][numOutNodes];
        for (int i = 0; i < numInNodes; i++) {
            System.arraycopy(other.weights[i], 0, this.weights[i], 0, numOutNodes);
        }

        this.biases = new double[numOutNodes];
        System.arraycopy(other.biases, 0, this.biases, 0, numOutNodes);
    }


    public void randFill() {
        //Normalized Xavier Weight Initialization
        double lower = -(Math.sqrt(6.0) / Math.sqrt((double) numInNodes + (double) numOutNodes));
        double upper = -lower;
        for (int i = 0; i < numInNodes; i++)
            for (int j = 0; j < numOutNodes; j++)
                weights[i][j] = lower + Math.random() * (upper - lower);
        //weights[i][j] = lower + Math.random() * (upper - lower);
    }

    public void randChange() {
        double weightScale = 0.2;
        double biasScale = 0.1;
        double weightLower = -weightScale;
        double weightUpper = -weightLower;
        for (int i = 0; i < numInNodes; i++) {
            for (int j = 0; j < numOutNodes; j++) {
                double weightRandomChange = 0;
                if (Math.random() > 1.0 - 1.0 / 2)
                    weightRandomChange = weightLower + Math.random() * (weightUpper - weightLower);
                weights[i][j] += weightRandomChange;
            }
        }
        // Adjust biases with random changes
        double biasLower = -biasScale;
        double biasUpper = -biasLower;
        for (int j = 0; j < numOutNodes; j++) {
            double biasRandomChange = 0.0;
            if (Math.random() > 1.0 - 1.0 / 2)
                biasRandomChange = biasLower + Math.random() * (biasUpper - biasLower);
            biases[j] += biasRandomChange;
        }
    }

    double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public double[] calculateOutLayer(double[] inputLayer) {
        double[] outLayer = new double[numOutNodes];
        for (int nodeOut = 0; nodeOut < numOutNodes; nodeOut++) {
            double inputSum = biases[nodeOut];
            for (int nodeIn = 0; nodeIn < numInNodes; nodeIn++)
                inputSum += inputLayer[nodeIn] * weights[nodeIn][nodeOut];
            outLayer[nodeOut] = sigmoid(inputSum);
        }
        return outLayer;
    }


}
