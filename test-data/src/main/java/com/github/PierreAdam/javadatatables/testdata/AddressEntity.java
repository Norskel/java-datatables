/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2025 Pierre Adam
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

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * AddressEntity.
 *
 * @author Pierre Adam
 * @since 21.04.01
 */
@Getter
@Setter
@NoArgsConstructor
public class AddressEntity {

    /**
     * The Uid.
     */
    private UUID uid;

    /**
     * The Street address.
     */
    private String streetAddress;

    /**
     * The City.
     */
    private String city;

    /**
     * The Zip code.
     */
    private String zipCode;

    /**
     * Instantiates a new Address entity.
     */
    AddressEntity(final Faker faker) {
        final Address address = faker.address();
        this.uid = UUID.randomUUID();
        this.streetAddress = address.streetAddress(true);
        this.city = address.city();
        this.zipCode = address.zipCode();
    }
}
