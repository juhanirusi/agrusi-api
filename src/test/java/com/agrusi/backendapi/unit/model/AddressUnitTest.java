package com.agrusi.backendapi.unit.model;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.enums.EAddressType;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.model.Address;
import com.agrusi.backendapi.model.Farm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @UnitTest --> Our custom annotation allowing us to run only
 * unit tests if we want to
*/

@UnitTest
public class AddressUnitTest {

    private Address address;

    private Account account;
    private Farm farm;

    private String streetAddress;
    private String city;
    private String province;
    private String postalCode;
    private String country;
    private Point location;
    private boolean isDefault;

    private Set<EAddressType> accountAddressTypes;
    private Set<EAddressType> farmAddressTypes;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    @BeforeEach
    public void setUp() {

        streetAddress = "My Street 123";
        city = "Kingston";
        province = "New York";
        postalCode = "12401";
        country = "United States";
        location = geometryFactory.createPoint(new Coordinate(0,1));
        isDefault = true;

        account = new Account();
        farm = new Farm();

        address = new Address();

        accountAddressTypes = Set.of(EAddressType.HOME);
        farmAddressTypes = Set.of(EAddressType.FARM_MAIN);
    }

    @Test
    @DisplayName("Test creation of Address for Account.")
    public void testAddressCreationForAccount() {

        // Set public fields with setters...
        address.setStreetAddress(streetAddress);
        address.setCity(city);
        address.setProvince(province);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        address.setLocation(location);
        address.setAccount(account);
        address.setDefault(isDefault);
        address.setAddressTypes(accountAddressTypes);

        Set<Address> accountAddresses = Set.of(address);
        account.setAddresses(accountAddresses);

        assertAll(
                "Grouped assertions of Account Address",
                () -> assertEquals(streetAddress, address.getStreetAddress()),
                () -> assertEquals(city, address.getCity()),
                () -> assertEquals(province, address.getProvince()),
                () -> assertEquals(postalCode, address.getPostalCode()),
                () -> assertEquals(country, address.getCountry()),
                () -> assertEquals(location, address.getLocation()),
                () -> assertEquals(isDefault, address.isDefault()),
                () -> assertEquals(account, address.getAccount()),
                () -> assertEquals(accountAddressTypes, address.getAddressTypes())
        );
    }

    @Test
    @DisplayName("Test creation of Address for Farm.")
    public void testAddressCreationForFarm() {

        // Set public fields with setters...
        address.setStreetAddress(streetAddress);
        address.setCity(city);
        address.setProvince(province);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        address.setLocation(location);
        address.setFarm(farm);
        address.setDefault(isDefault);
        address.setAddressTypes(farmAddressTypes);

        Set<Address> farmAddresses = Set.of(address);
        farm.setAddresses(farmAddresses);

        assertAll(
                "Grouped assertions of Farm Address",
                () -> assertEquals(streetAddress, address.getStreetAddress()),
                () -> assertEquals(city, address.getCity()),
                () -> assertEquals(province, address.getProvince()),
                () -> assertEquals(postalCode, address.getPostalCode()),
                () -> assertEquals(country, address.getCountry()),
                () -> assertEquals(location, address.getLocation()),
                () -> assertEquals(isDefault, address.isDefault()),
                () -> assertEquals(farm, address.getFarm()),
                () -> assertEquals(farmAddressTypes, address.getAddressTypes())
        );
    }
}
