package FlowCollect.processor;

import FlowCollect.vo.Netflow;

public interface FlowProcessor{
    public Netflow flowProcess(Netflow input);

}
