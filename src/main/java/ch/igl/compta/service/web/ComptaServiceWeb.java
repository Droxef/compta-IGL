package ch.igl.compta.service.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.igl.compta.model.ComptaPlan;
import ch.igl.compta.repository.web.ComptaProxy;
import lombok.Data;

import java.util.List;

import org.springframework.data.util.Pair;

import ch.igl.compta.model.ComptaCompte;
import ch.igl.compta.model.ComptaCompteGroupe;

@Data
@Service
public class ComptaServiceWeb {

    @Autowired
    private ComptaProxy comptaProxy;

    public ComptaPlan getLastPlanActif() {
        return comptaProxy.getLastplanActif();
    }

    public ComptaPlan getPlanById(final long id)  {
        return comptaProxy.getPlanById(id);
    }

    public List<Pair<Long, String>> getPlansInfo() {
        return comptaProxy.getPlansInfo();
    }

    public ComptaPlan savePlan(ComptaPlan p) {
        ComptaPlan plan;

        if(p.getId() == null) {
            plan = comptaProxy.createPlan(p);
        } else {
            plan = comptaProxy.modifyPlan(p);
        }

        return plan;
    }

    public ComptaPlan deletePlan(final long id) {
        return comptaProxy.deletePlan(id);
    }

    public ComptaPlan reopenPlan(final long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public ComptaPlan closePlan(final ComptaPlan plan) {
        // TODO check compta rules before closing
        plan.setOpen(false);
        return comptaProxy.modifyPlan(plan);
    }

    public ComptaPlan closePlan(final long id) {
        throw new UnsupportedOperationException("Not implemented yet");
//        return comptaProxy.closePlan(id);
    }

    public List<ComptaCompte> getComptesByPlanId(final long planId) {
        return comptaProxy.getComptesByPlanId(planId);
    }

    public List<ComptaCompteGroupe> getGroupesByPlanId(final long planId) {
        return comptaProxy.getGroupesByPlanId(planId);
    }
}
