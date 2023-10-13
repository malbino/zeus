/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facades.negocio;

import edu.infocalcbba.argos.entities.Cuota;
import edu.infocalcbba.argos.entities.Derecho;
import edu.infocalcbba.argos.entities.Nota;
import edu.infocalcbba.phoenix.entities.Pago;
import edu.infocalcbba.phoenix.entities.PlanPagoPh;
import edu.infocalcbba.zeus.entities.Detalle;
import edu.infocalcbba.zeus.entities.Factura;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tincho
 */
@Stateless
@LocalBean
public class AnularFacturaFacade {

    private static final String CONDICION_ADEUDADA = "ADEUDADA";

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean anularFactura(Factura factura) {
        boolean b = true;

        try {
            em.merge(factura);

            List<Detalle> detalles = factura.getDetalles();
            for (Detalle detalle : detalles) {
                Cuota cuota = detalle.getCuota();
                if (cuota != null) {
                    cuota.setPagado(cuota.getPagado() - detalle.getTotal().intValue());
                    cuota.setAdeudado(cuota.getAdeudado() + detalle.getTotal().intValue());
                    if (cuota.getAdeudado() > 0) {
                        cuota.setCondicion(CONDICION_ADEUDADA);
                    }
                    em.merge(cuota);
                }

                Pago pago = detalle.getPago();
                if (pago != null) {
                    pago.setPagado(pago.getPagado() - detalle.getTotal().intValue());
                    pago.setAdeudado(pago.getAdeudado() + detalle.getTotal().intValue());
                    if (pago.getAdeudado() > 0) {
                        pago.setCondicion(CONDICION_ADEUDADA);
                    }
                    em.merge(pago);

                    PlanPagoPh planPago = pago.getPlanpago();
                    planPago.setPagado(planPago.getPagado() - detalle.getTotal().intValue());
                    planPago.setAdeudado(planPago.getAdeudado() + detalle.getTotal().intValue());
                    if (pago.getAdeudado() > 0) {
                        planPago.setCondicion(CONDICION_ADEUDADA);
                    }
                    em.merge(planPago);
                }

                Derecho derecho = detalle.getDerecho();
                if (derecho != null) {
                    derecho.setPagado(derecho.getPagado() - detalle.getTotal().intValue());
                    derecho.setAdeudado(derecho.getAdeudado() + detalle.getTotal().intValue());
                    if (derecho.getAdeudado() > 0) {
                        derecho.setCondicion(CONDICION_ADEUDADA);
                    }
                    em.merge(derecho);
                }

                Nota nota = detalle.getNota();
                if (nota != null) {
                    detalle.setNota(null);

                    em.merge(detalle);
                }
            }
        } catch (Exception e) {
            b = false;
        }

        return b;
    }

}
