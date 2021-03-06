/**
 * Copyright 2014 Comcast Cable Communications Management, LLC
 *
 * This file is part of CATS.
 *
 * CATS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CATS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CATS.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.comcast.cats.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The plant to which the settop is connected.
 * 
 * @author minu
 * 
 */
@XmlRootElement
public class RFPlant extends Domain
{
    private static final long serialVersionUID = 6390478200355654739L;

    private Controller        controller;

    public RFPlant()
    {
        // TODO Auto-generated constructor stub
    }

    public RFPlant( String id )
    {
        super( id );
    }

    @XmlElement
    public Controller getController()
    {
        return controller;
    }

    public void setController( Controller controller )
    {
        this.controller = controller;
    }

    @Override
    public String toString()
    {
        return super.toString() + getClass().getName() + " [controller=" + getController() + "]";
    }
}
