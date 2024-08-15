package FlowCollect.writer;


import FlowCollect.vo.Netflow;
import FlowCollect.db.jdbc.FlowJdbc;
import Prop.PropData;
import Util.NCutil;

import java.sql.SQLException;
import java.util.List;

public class JdbcWriter implements Runnable {
    List<Netflow> list;
    FlowJdbc flowJdbc;

    public JdbcWriter() {

    }

    public JdbcWriter(List<Netflow> list, FlowJdbc flowJdbc) {
        this.list = list;
        this.flowJdbc = flowJdbc;
    }

    @Override
    public void run() {
        try {
            flowJdbc.insertNetflowBTL(list);
            flowJdbc.clearConnection();
        } catch (SQLException e) {
            NCutil.log_msg(e.getMessage());
            try {
                flowJdbc.clearConnection();
            } catch (SQLException ex) {
                NCutil.log_msg(ex.getMessage());
            }
        }
    }
}
