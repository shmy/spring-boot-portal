package tech.shmy.portal.application.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.CasbinRule;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CasbinService extends ServiceImpl<CasbinRule.CasbinRuleMapper, CasbinRule> {
    @Autowired
    private Enforcer enforcer;
    // 角色
    public List<String> getAllRoles() {
        List<CasbinRule> casbinRules = query()
                .eq("ptype", "p")
                .select("distinct v0")
                .list();
        // TODO: 获取对应角色描述
        return casbinRules.stream().map((CasbinRule::getV0)).collect(Collectors.toList());

    }
    public CasbinRule addRoleWithPermission(Policy policy) throws Exception {
        policy.setSubject(beautifyRoleName(policy.getSubject()));
        if (!enforcer.addPolicy(policy.getSubject(), policy.getObject(), policy.getAction())) {
            throw new Exception("添加失败");
        }
        return getResult(policy, "p");
    }
    public CasbinRule updateRoleWithPermission(Policy policy, Policy newPolicy) throws Exception {
        policy.setSubject(beautifyRoleName(policy.getSubject()));
        newPolicy.setSubject(beautifyRoleName(newPolicy.getSubject()));
        if (!enforcer.removePolicy(policy.getSubject(), policy.getObject(), policy.getAction())) {
            throw new Exception("更新失败");
        }
        if (!enforcer.addPolicy(newPolicy.getSubject(), newPolicy.getObject(), newPolicy.getAction())) {
            throw new Exception("更新失败");
        }
        return getResult(newPolicy, "p");
    }
    public CasbinRule removePermissionForRole(Policy policy) throws Exception {
        policy.setSubject(beautifyRoleName(policy.getSubject()));
        if (!enforcer.removePolicy(policy.getSubject(), policy.getObject(), policy.getAction())) {
            throw new Exception("删除失败");
        }
        return getResult(policy, "p");
    }
    // 用户
    public CasbinRule addUserWithRole(Policy policy) throws Exception {
        policy.setSubject(beautifyUserName(policy.getSubject()));
        if (!enforcer.addGroupingPolicy(policy.getSubject(), policy.getObject(), policy.getAction())) {
            throw new Exception("添加失败");
        }
        return getResult(policy, "g");
    }
    public CasbinRule updateUserWithRole(Policy policy, Policy newPolicy) throws Exception {
        policy.setSubject(beautifyUserName(policy.getSubject()));
        newPolicy.setSubject(beautifyUserName(newPolicy.getSubject()));
        if (!enforcer.removeGroupingPolicy(policy.getSubject(), policy.getObject(), policy.getAction())) {
            throw new Exception("更新失败");
        }
        if (!enforcer.addGroupingPolicy(newPolicy.getSubject(), newPolicy.getObject(), newPolicy.getAction())) {
            throw new Exception("更新失败");
        }
        return getResult(newPolicy, "g");
    }
    public CasbinRule removeRoleForUser(Policy policy) throws Exception {
        policy.setSubject(beautifyUserName(policy.getSubject()));
        if (!enforcer.removeGroupingPolicy(policy.getSubject(), policy.getObject(), policy.getAction())) {
            throw new Exception("删除失败");
        }
        return getResult(policy, "g");
    }
    private String beautifyRoleName(String name) {
        return "role::" + name;
    }
    private String beautifyUserName(String name) {
        return "user::" + name;
    }
    private CasbinRule getResult(Policy policy, String ptype) {
        CasbinRule casbinRule = new CasbinRule();
        casbinRule.setPtype(ptype);
        casbinRule.setV0(policy.getSubject());
        casbinRule.setV1(policy.getObject());
        casbinRule.setV2(policy.getAction());
        return casbinRule;
    }
    @Data
    public static class Policy {
        private String subject;
        private String object;
        private String action;
    }
}
