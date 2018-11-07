package servlets.s.shop.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityBranch;
import spring.entity.EntityShop;
import spring.interfaces.BranchDao;
import spring.interfaces.ShopDao;
import java.util.LinkedList;
import java.util.List;

public class BranchServletService {

    private BranchDao branchDao;
    private ShopDao shopDao;

    public BranchServletService(WebApplicationContext ctx) {

        branchDao = ctx.getBean("jpaBranch", BranchDao.class);
        shopDao = ctx.getBean("jpaShop",ShopDao.class);
    }

    public boolean saveBranches(EntityShop shop, JSONArray branchesArrayJ) {

        List<EntityBranch> branchList = new LinkedList<>();

        for (int i = 0; i < branchesArrayJ.length(); i++) {

            JSONObject branchJ = branchesArrayJ.getJSONObject(i);

            JSONObject checkJ = new JSONObject();

            if (branchJ.similar(checkJ)) {

                return false;
            }

            EntityBranch branch;

            if (branchJ.has("branch_id")) {

                branch = branchDao.findById(branchJ.getLong("branch_id"));

                if (branch == null) {

                    return false;
                }

            } else {

                branch = new EntityBranch();
            }

            branch.setShop(shop);

            if (branchJ.has("address")) {
                branch.setAddress(branchJ.getString("address"));
            }

            if (branchJ.has("branch_name")) {
                branch.setName(branchJ.getString("branch_name"));
            }

            if (branchJ.has("branch_phone")) {
                branch.setPhone(branchJ.getLong("branch_phone"));
            }

            if (branchJ.has("longitude") && branchJ.has("latitude")) {

                branch.setLongitude(branchJ.getDouble("longitude"));
                branch.setLatitude(branchJ.getDouble("latitude"));
            }

            branchList.add(branch);
        }

        shop.setEntityBranchList(branchList);

        shopDao.save(shop);

        return true;
    }

    public JSONArray getBranchesArrayJ(List<EntityBranch> branchList) {

        if (branchList == null || branchList.size() == 0) {

            return null;
        }

        JSONArray branchesArrayJ = new JSONArray();

        for (EntityBranch branch : branchList) {

            JSONObject branchJ = new JSONObject();

            branchJ.put("id", branch.getId());

            if (branch.getAddress() != null) {
                branchJ.put("address", branch.getAddress());
            }

            if (branch.getName() != null) {
                branchJ.put("name", branch.getName());
            }

            if (branch.getPhone() != 0) {
                branchJ.put("phone", branch.getPhone());
            }

            if (branch.getLongitude() != 0 && branch.getLatitude() != 0) {
                branchJ.put("longitude", branch.getLongitude());
                branchJ.put("latitude", branch.getLatitude());
            }

            branchesArrayJ.put(branchJ);
        }

        return branchesArrayJ;
    }
}
