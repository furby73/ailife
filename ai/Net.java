package ai;

public class Net {
    Layer[] layers;

    public Net(int[] layersSizes) {
        layers = new Layer[layersSizes.length - 1];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Layer(layersSizes[i], layersSizes[i + 1]);
        }
    }

    public Net(Net network) {
        layers = new Layer[network.layers.length];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Layer(network.layers[i]);
        }
        for (Layer layer : layers) {
            layer.randChange();
        }
    }

    public double[] outputLayer(double[] input) {
        for (Layer l : layers) {
            input = l.calculateOutLayer(input);
        }
        return input;
    }

}
