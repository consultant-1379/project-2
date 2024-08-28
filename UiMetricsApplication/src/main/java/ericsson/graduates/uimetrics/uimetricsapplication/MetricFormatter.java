package ericsson.graduates.uimetrics.uimetricsapplication;

public class MetricFormatter {
    private final long[] metrics;
    private float unitSuccessRate;
    private float integrationSuccessRate;
    public MetricFormatter(long[] metrics) {
        this.metrics = metrics;
    }
    void calculateRates()
    {
        if(this.metrics[1]==0)
            this.unitSuccessRate=1;
        else
        {
            this.unitSuccessRate=(float)this.metrics[0]/(this.metrics[0]+this.metrics[1]);
        }
        if(this.metrics[3]==0)
            this.integrationSuccessRate=1;
        else
        {
            this.integrationSuccessRate=(float)this.metrics[2]/(this.metrics[2]+this.metrics[3]);
        }
    }

    @Override
    public String toString() {
        this.calculateRates();
        return String.format("Total unit tests passed:%s \n Total unit tests failed:%s \n Total Integration Tests passed" +
                "%s \n Total Integration tests failed:%s \n Unit test Pass rate:%s ,fail rate:%s \n Integration test " +
                "Pass rate:%s,fail rate:%s",metrics[0],metrics[1],metrics[2],metrics[3],unitSuccessRate
                ,1-unitSuccessRate,integrationSuccessRate,1-integrationSuccessRate);
    }
}
