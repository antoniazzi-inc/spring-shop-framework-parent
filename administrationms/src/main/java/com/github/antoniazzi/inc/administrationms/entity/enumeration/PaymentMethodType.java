/*
 * Copyright 2018-2021 Antoniazzi Holding BV
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.antoniazzi.inc.administrationms.entity.enumeration;

/**
 * PaymentMethodType
 *
 * @version 1.0.0
 * @since 22.02.2021
 * @author Kristijan Georgiev
 *
 */
public enum PaymentMethodType {

	CREDIT_CARD("CREDIT_CARD"), IDEAL("IDEAL"), BANK_TRANSFER("BANK_TRANSFER"), PAYPAL("PAYPAL"), PAYPAL_NATIVE("PAYPAL_NATIVE"),
	RECURRENT_INCASSO("RECURRENT_INCASSO"), SOFORT("SOFORT"), IDEAL_SISOW("IDEAL_SISOW"), BANCONTACT_SISOW("BANCONTACT_SISOW"), BANCONTACT("BANCONTACT"),
	BELFIUS("BELFIUS"), KBC("KBC"), APPLE_PAY("APPLE_PAY"), ONETIME_INCASSO("ONETIME_INCASSO");

	public final String label;

	private PaymentMethodType(String label) {
		this.label = label;
	}
}
