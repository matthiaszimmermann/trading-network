package org.eclipse.scout.tradingnetwork.shared.organization;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "org.eclipse.scout.tradingnetwork.client.organization.OrganizationTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class OrganizationTablePageData extends AbstractTablePageData {

  private static final long serialVersionUID = 1L;

  @Override
  public OrganizationTableRowData addRow() {
    return (OrganizationTableRowData) super.addRow();
  }

  @Override
  public OrganizationTableRowData addRow(int rowState) {
    return (OrganizationTableRowData) super.addRow(rowState);
  }

  @Override
  public OrganizationTableRowData createRow() {
    return new OrganizationTableRowData();
  }

  @Override
  public Class<? extends AbstractTableRowData> getRowType() {
    return OrganizationTableRowData.class;
  }

  @Override
  public OrganizationTableRowData[] getRows() {
    return (OrganizationTableRowData[]) super.getRows();
  }

  @Override
  public OrganizationTableRowData rowAt(int index) {
    return (OrganizationTableRowData) super.rowAt(index);
  }

  public void setRows(OrganizationTableRowData[] rows) {
    super.setRows(rows);
  }

  public static class OrganizationTableRowData extends AbstractTableRowData {

    private static final long serialVersionUID = 1L;
    public static final String organizationId = "organizationId";
    public static final String name = "name";
    public static final String city = "city";
    public static final String country = "country";
    public static final String homepage = "homepage";
    private String m_organizationId;
    private String m_name;
    private String m_city;
    private String m_country;
    private String m_homepage;

    public String getOrganizationId() {
      return m_organizationId;
    }

    public void setOrganizationId(String newOrganizationId) {
      m_organizationId = newOrganizationId;
    }

    public String getName() {
      return m_name;
    }

    public void setName(String newName) {
      m_name = newName;
    }

    public String getCity() {
      return m_city;
    }

    public void setCity(String newCity) {
      m_city = newCity;
    }

    public String getCountry() {
      return m_country;
    }

    public void setCountry(String newCountry) {
      m_country = newCountry;
    }

    public String getHomepage() {
      return m_homepage;
    }

    public void setHomepage(String newHomepage) {
      m_homepage = newHomepage;
    }
  }
}
