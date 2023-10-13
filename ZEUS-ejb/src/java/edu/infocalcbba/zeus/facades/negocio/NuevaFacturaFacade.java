/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facades.negocio;

import edu.infocalcbba.argos.entities.Cuota;
import edu.infocalcbba.argos.entities.Derecho;
import edu.infocalcbba.phoenix.entities.Pago;
import edu.infocalcbba.phoenix.entities.PlanPagoPh;
import edu.infocalcbba.util.Redondeo;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Detalle;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import edu.infocalcbba.zeus.facturacion.cc7.CC7;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tincho
 */
@Stateless
@LocalBean
public class NuevaFacturaFacade {

    private static final Logger log = LoggerFactory.getLogger(NuevaFacturaFacade.class);

    private static final String CONDICION_PAGADA = "PAGADA";

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    @EJB
    FacturaFacade facturaFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean nuevaFactura(Factura factura, List<Detalle> detalles) {
        boolean b = true;

        try {
            long cantidadPorDosificacion = facturaFacade.cantidadPorDosificacion(factura.getDosificacion().getId_dosificacion());
            int numero = (int) (cantidadPorDosificacion + 1);
            factura.setNumero(numero);

            String numero_autorizacion = String.valueOf(factura.getDosificacion().getNumero_autorizacion());
            log.info("Numero de Autorizacion: " + numero_autorizacion);
            String numero_factura = String.valueOf(factura.getNumero());
            log.info("Numero de Factura: " + numero_factura);
            String nit_ci = String.valueOf(factura.getCliente().getNit_ci());
            log.info("NIT/CI: " + nit_ci);
            String fecha_transaccion = Reloj.formatearFecha_yyyyMMdd(factura.getFecha());
            log.info("Fecha Transaccion: " + fecha_transaccion);
            Double montoRedondeado = Redondeo.redondear_HALFUP(factura.getMonto(), 0);
            String monto_transaccion = String.valueOf(montoRedondeado.intValue());
            log.info("Monto Transaccion: " + monto_transaccion);
            String llave_dosificacion = factura.getDosificacion().getLlave_dosificacion();
            log.info("LLave Dosificacion: " + llave_dosificacion);
            String codigo_control = CC7.obtener(numero_autorizacion, numero_factura, nit_ci, fecha_transaccion, monto_transaccion, llave_dosificacion);
            log.info("CC7: " + codigo_control);
            factura.setCodigo_control(codigo_control);

            em.persist(factura);

            for (Detalle detalle : detalles) {
                detalle.setFactura(factura);
                em.persist(detalle);

                Cuota cuota = detalle.getCuota();
                if (cuota != null) {
                    cuota.setPagado(cuota.getPagado() + detalle.getTotal().intValue());
                    cuota.setAdeudado(cuota.getAdeudado() - detalle.getTotal().intValue());
                    if (cuota.getAdeudado() == 0) {
                        cuota.setCondicion(CONDICION_PAGADA);
                    }
                    em.merge(cuota);
                }

                Pago pago = detalle.getPago();
                if (pago != null) {
                    pago.setPagado(pago.getPagado() + detalle.getTotal().intValue());
                    pago.setAdeudado(pago.getAdeudado() - detalle.getTotal().intValue());
                    if (pago.getAdeudado() == 0) {
                        pago.setCondicion(CONDICION_PAGADA);
                    }
                    em.merge(pago);

                    PlanPagoPh planpago = pago.getPlanpago();
                    planpago.setPagado(planpago.getPagado() + detalle.getTotal().intValue());
                    planpago.setAdeudado(planpago.getAdeudado() - detalle.getTotal().intValue());
                    if (planpago.getAdeudado() == 0) {
                        planpago.setCondicion(CONDICION_PAGADA);
                    }
                    em.merge(planpago);
                }

                Derecho derecho = detalle.getDerecho();
                if (derecho != null) {
                    derecho.setPagado(derecho.getPagado() + detalle.getTotal().intValue());
                    derecho.setAdeudado(derecho.getAdeudado() - detalle.getTotal().intValue());
                    if (derecho.getAdeudado() == 0) {
                        derecho.setCondicion(CONDICION_PAGADA);
                    }
                    em.merge(derecho);
                }
            }
        } catch (Exception e) {
            b = false;
        }

        return b;
    }
}
