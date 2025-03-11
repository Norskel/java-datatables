/*
 * MIT License
 *
 * Copyright (c) 2021 Pierre Adam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.PierreAdam.javadatatables.testdata;

import com.github.PierreAdam.javadatatables.testdata.enums.NumberEnum;
import com.github.PierreAdam.javadatatables.testdata.enums.SimpleEnum;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * PersonEntity.
 *
 * @author Pierre Adam
 * @since 21.03.02
 */
@Getter
@Setter
@NoArgsConstructor
public class PersonEntity {

    /**
     * The Created at.
     */
    private OffsetDateTime createdAt;

    /**
     * The Uid.
     */
    private UUID uid;

    /**
     * The First name.
     */
    private String firstName;

    /**
     * The Last name.
     */
    private String lastName;

    /**
     * The Title.
     */
    private String title;

    /**
     * The Blood group.
     */
    private String bloodGroup;

    /**
     * The Is active.
     */
    private Boolean active;

    /**
     * The Long number.
     */
    private Long longNumber;

    /**
     * The Integer number.
     */
    private Integer integerNumber;

    /**
     * The Double number.
     */
    private Double doubleNumber;

    /**
     * The Float number.
     */
    private Float floatNumber;

    /**
     * The Big integer number.
     */
    private BigInteger bigIntegerNumber;

    /**
     * The Big decimal number.
     */
    private BigDecimal bigDecimalNumber;

    /**
     * The Simple enum.
     */
    private SimpleEnum simpleEnum;

    /**
     * The Number enum.
     */
    private NumberEnum numberEnum;

    /**
     * The Null data.
     */
    private String nullData;

    /**
     * The Address.
     */
    private AddressEntity address;

    /**
     * Instantiates a new Person entity.
     */
    public PersonEntity(final Faker faker) {
        final Random random = new Random();
        final Name name = faker.name();
        this.createdAt = OffsetDateTime.now().minusSeconds(random.nextInt(3600 * 24 * 200)); // Over the last 200 days
        this.uid = UUID.randomUUID();
        this.firstName = name.firstName();
        this.lastName = name.lastName();
        this.title = name.title();
        this.bloodGroup = name.bloodGroup();
        this.active = random.nextBoolean();
        this.longNumber = random.nextLong();
        this.integerNumber = random.nextInt();
        this.doubleNumber = random.nextDouble();
        this.floatNumber = random.nextFloat();
        this.bigIntegerNumber = BigInteger.valueOf(random.nextLong());
        this.bigDecimalNumber = BigDecimal.valueOf(random.nextDouble());
        this.nullData = null;

        this.simpleEnum = SimpleEnum.rand(random);
        this.numberEnum = NumberEnum.rand(random);

        this.address = new AddressEntity(faker);
    }
}
