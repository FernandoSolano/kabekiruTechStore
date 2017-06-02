package cr.ac.ucr.kabekuritechstore.data;

import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import cr.ac.ucr.kabekuritechstore.domain.Order;
import cr.ac.ucr.kabekuritechstore.domain.OrderDetail;

@Repository
public class ShoppingCartDao {
	private SimpleJdbcCall simpleJdbcCallOrder;
	private SimpleJdbcCall simpleJdbcCallOrderDetail;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.simpleJdbcCallOrder = new SimpleJdbcCall(dataSource).withProcedureName("sp_order_insert");
		this.simpleJdbcCallOrderDetail = new SimpleJdbcCall(dataSource).withProcedureName("sp_order_detail_insert");
	}
	
	public Order insertOrder(Order order){
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("p_user_id",order.getUser().getUserID())
				.addValue("p_total_value",order.getTotal());
		
		Map<String, Object> outParameter = simpleJdbcCallOrder.execute(parameterSource);
		order.setOrderID(Integer.parseInt((outParameter.get("p_id").toString())));
		order.setOrderDate((java.sql.Date) new Date(outParameter.get("p_order_date").toString()));
		order.setShipDate((java.sql.Date) new Date(outParameter.get("p_ship_date").toString()));
		order.setTrackingNumber(outParameter.get("p_tracking_number").toString());
		order.setOrderStatusID(Integer.parseInt(outParameter.get("p_status").toString()));
		
		for (OrderDetail orderDetail : order.getOrderDetails()) {
			
			orderDetail.setOrder(order);
			
			SqlParameterSource parameterSourceDetail = new MapSqlParameterSource()
					.addValue("p_order_id", order.getOrderID())
					.addValue("p_product_id",orderDetail.getProduct().getId())
					.addValue("p_quantity",orderDetail.getQuantity())
					.addValue("p_price",orderDetail.getPrice() * orderDetail.getQuantity());
			
			Map<String, Object> outParameterDetail = simpleJdbcCallOrderDetail.execute(parameterSourceDetail);
			
			orderDetail.setId(Integer.parseInt(outParameterDetail.get("p_id").toString()));
		}
		
		return order;
	}

}
