/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.tradingnetwork.client.order;

import java.math.BigDecimal;
import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.tradingnetwork.client.common.AbstractDirtyFormHandler;
import org.eclipse.scout.tradingnetwork.client.order.DealForm.MainBox.CancelButton;
import org.eclipse.scout.tradingnetwork.client.order.DealForm.MainBox.GeneralBox;
import org.eclipse.scout.tradingnetwork.client.order.DealForm.MainBox.GeneralBox.DealFormGroupBox;
import org.eclipse.scout.tradingnetwork.client.order.DealForm.MainBox.GeneralBox.DealFormGroupBox.ExchangeRateField;
import org.eclipse.scout.tradingnetwork.client.order.DealForm.MainBox.GeneralBox.DealFormGroupBox.OrderBookTypeField;
import org.eclipse.scout.tradingnetwork.client.order.DealForm.MainBox.GeneralBox.DealFormGroupBox.QuantityField;
import org.eclipse.scout.tradingnetwork.client.order.DealForm.MainBox.GeneralBox.DealFormGroupBox.TradingActionBox;
import org.eclipse.scout.tradingnetwork.client.order.DealForm.MainBox.OkButton;
import org.eclipse.scout.tradingnetwork.shared.order.DealFormData;
import org.eclipse.scout.tradingnetwork.shared.order.IDealService;
import org.eclipse.scout.tradingnetwork.shared.order.OrderBookTypeCodeType;
import org.eclipse.scout.tradingnetwork.shared.order.TradingActionCodeType;
import org.eclipse.scout.tradingnetwork.shared.order.UpdateDealPermission;

@FormData(value = DealFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class DealForm extends AbstractForm {

  public static final int ETHER_MAX_FRACTION_DIGIT = 6;

  private Long dealId;

  private String m_organizationId;
  private String m_dealNr;
  private String m_status;
  private String m_publishTransactionHash;

  @FormData
  public String getStatus() {
    return m_status;
  }

  @FormData
  public void setStatus(String status) {
    this.m_status = status;
  }

  @FormData
  public String getOrganizationId() {
    return m_organizationId;
  }

  @FormData
  public String getDealNr() {
    return m_dealNr;
  }

  @FormData
  public void setDealNr(String dealNr) {
    this.m_dealNr = dealNr;
  }

  @FormData
  public void setOrganizationId(String organizationId) {
    this.m_organizationId = organizationId;
  }

  @FormData
  public Long getDealId() {
    return dealId;
  }

  @FormData
  public void setDealId(Long dealId) {
    this.dealId = dealId;
  }

  @FormData
  public String getPublishTransactionHash() {
    return m_publishTransactionHash;
  }

  @FormData
  public void setPublishTransactionHash(String publishTransactionHash) {
    m_publishTransactionHash = publishTransactionHash;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Deal");
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return IForm.DISPLAY_HINT_DIALOG;
  }

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  @Override
  public void execStored() {
    getDesktop().dataChanged(IDealService.NotificationEnum.Deals);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public GeneralBox getGeneralBox() {
    return getFieldByClass(GeneralBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public DealFormGroupBox getDealFormGroupBox() {
    return getFieldByClass(DealFormGroupBox.class);
  }

  public ExchangeRateField getExchangeRateField() {
    return getFieldByClass(ExchangeRateField.class);
  }

  public TradingActionBox getTradingActionGroupBox() {
    return getFieldByClass(TradingActionBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public OrderBookTypeField getOrderBookTypeField() {
    return getFieldByClass(OrderBookTypeField.class);
  }

  public QuantityField getQuantityField() {
    return getFieldByClass(QuantityField.class);
  }

  @Override
  public Object computeExclusiveKey() {
    return getDealId();
  }

  @Order(1)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class GeneralBox extends AbstractGroupBox {
      @Override
      protected int getConfiguredGridW() {
        return 2;
      }

      @Override
      protected int getConfiguredGridH() {
        return 1;
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Order(-1000)
      public class DealFormGroupBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Details");
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(-1000)
        public class TradingActionBox extends AbstractRadioButtonGroup<String> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Action");
          }

          @Override
          public void setMandatory(boolean b) {
            super.setMandatory(true);
          }

          @Override
          protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
            return TradingActionCodeType.class;
          }

        }

        @Order(10)
        public class OrderBookTypeField extends AbstractSmartField<String> {
          @Override
          public void setMandatory(boolean b) {
            super.setMandatory(true);
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("OrderBookType");
          }

          @Override
          protected Class<OrderBookTypeCodeType> getConfiguredCodeType() {
            return OrderBookTypeCodeType.class;
          }

          // TODO execFilterBrowseLookupResult(): Liste neuladen und filtern.
        }

        @Order(20)
        public class QuantityField extends AbstractLongField {

          @Override
          public void setMandatory(boolean b) {
            super.setMandatory(true);
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Quantity");
          }

          @Override
          protected Long execValidateValue(Long rawValue) {
            if (null != rawValue && rawValue <= 0) {
              throw new VetoException(TEXTS.get("AmountValidationError"));
            }
            return super.execValidateValue(rawValue);
          }
        }

        @Order(30)
        public class ExchangeRateField extends AbstractBigDecimalField {
          @Override
          public void setMandatory(boolean b) {
            super.setMandatory(true);
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ExchangeRate");
          }

          @Override
          protected int getConfiguredGridW() {
            return 1;
          }

          @Override
          protected BigDecimal getConfiguredMinValue() {
            return new BigDecimal(10 ^ (-ETHER_MAX_FRACTION_DIGIT));
          }

          @Override
          protected BigDecimal getConfiguredMaxValue() {
            return new BigDecimal("100000");
          }

          @Override
          protected int getConfiguredMaxFractionDigits() {
            return ETHER_MAX_FRACTION_DIGIT;
          }

          @Override
          protected BigDecimal execValidateValue(BigDecimal rawValue) {
            if (null != rawValue && rawValue.signum() <= 0) {
              throw new VetoException(TEXTS.get("SellAmountValidationError"));
            }
            return super.execValidateValue(rawValue);
          }

          public void loadCurrentExchangeRate() {
            if (StringUtility.hasText(getTradingActionGroupBox().getValue())
                && StringUtility.hasText(getOrderBookTypeField().getValue())) {
              try {
                Double currentRate = BEANS.get(IDealService.class).getCurrentExchangeRate(
                    getOrderBookTypeField().getValue(), getTradingActionGroupBox().getValue());
                if (null != currentRate) {
                  setValue(BigDecimal.valueOf(currentRate));
                }
              }
              catch (ProcessingException e) {
                MessageBoxes.createOk().withBody(TEXTS.get("CouldNotLoadExchangeRate")).withSeverity(IStatus.ERROR).show();
              }
            }
          }

          @Order(1000)
          public class LoadExchangeRateMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return TEXTS.get("LoadCurrentExchangeRate");
            }

            @Override
            protected String getConfiguredIconId() {
              return "font:awesomeIcons \uf0ec";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(ValueFieldMenuType.NotNull, ValueFieldMenuType.Null);
            }

            @Override
            protected void execAction() {
              getExchangeRateField().loadCurrentExchangeRate();
            }
          }
        }
      }
    }

    @Order(100)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractDirtyFormHandler {

    @Override
    protected void execLoad() {
      DealFormData formData = new DealFormData();
      exportFormData(formData);
      formData = BEANS.get(IDealService.class).load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateDealPermission());
    }

    @Override
    protected void execStore() {
      DealFormData formData = new DealFormData();
      exportFormData(formData);
      formData = BEANS.get(IDealService.class).store(formData);
    }

    @Override
    protected boolean getConfiguredOpenExclusive() {
      return true;
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      DealFormData formData = new DealFormData();
      exportFormData(formData);
      formData = BEANS.get(IDealService.class).prepareCreate(formData);
      formData = initFormDataValues(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      DealFormData formData = new DealFormData();
      exportFormData(formData);
      formData = BEANS.get(IDealService.class).create(formData);
    }

    private DealFormData initFormDataValues(DealFormData formData) {
      formData.getTradingActionBox().setValue(TradingActionCodeType.BuyCode.ID);
      formData.getOrderBookType().setValue(OrderBookTypeCodeType.UsdEurCode.ID);

      return formData;
    }
  }
}
