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
package com.comcast.cats.domain.service.it;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.comcast.cats.domain.Domain;
import com.comcast.cats.domain.SettopDesc;
import com.comcast.cats.domain.service.DomainService;
import com.comcast.cats.domain.service.SettopDomainService;

/**
 * 
 * @author subinsugunan
 * 
 */
public class SettopImportIT extends BaseDomainIT
{
    @Inject
    protected SettopDomainService settopDomainService;

    @Override
    public void setup()
    {
        super.setup();
    }

    @Override
    @Test
    public void testContext()
    {
        super.testContext();
        Assert.assertNotNull( settopDomainService );
    }

    @Test
    public void testFindAll() throws Exception
    {
        importDomain( settopDomainService, SettopDesc.class, false );
    }

    private void importDomain( DomainService< ? extends Domain > domainService, Class< ? extends Domain > clazz,
            boolean activeOnly )
    {
        LOGGER.info( "Importing [" + clazz.getSimpleName() + "] - START" );

        int totalResults = 0;

        if ( activeOnly )
        {
            totalResults = domainService.countActive();
        }
        else
        {
            totalResults = domainService.count();
        }

        int maxBatchSize = 50;
        int totalPages = ( int ) ( Math.ceil( ( double ) totalResults / ( double ) maxBatchSize ) );

        LOGGER.info( "Total records to be imported [" + totalResults + "]" );
        LOGGER.info( "Records will be imported in  [" + totalPages + "] batches with a maximum of [" + maxBatchSize
                + "] per batch" );

        int page = 1;
        int offset = 0;

        List< ? extends Domain > list = null;
        int importCount = 0;

        while ( page <= totalPages )
        {
            int count = getCount( page, totalResults, maxBatchSize );

            LOGGER.info( "Retrieving data [CurrentPage/TotalPages][" + page + "/" + totalPages + "][Offset][" + offset
                    + "][Count][" + count + "]" );

            if ( activeOnly )
            {
                list = domainService.findActive( offset, count );
            }
            else
            {
                list = domainService.find( offset, count );
            }

            if ( ( null != list ) && ( !list.isEmpty() ) )
            {
                LOGGER.info( "Retrieved " + list.size() + " records" );

                for ( Domain domain : list )
                {
                    try
                    {
                        LOGGER.info( "Creating [" + clazz.getSimpleName() + "] [" + ( ++importCount ) + "/"
                                + totalResults + "] [" + domain + "]" );

                        if ( domain instanceof SettopDesc )
                        {
                            createSettop( ( SettopDesc ) domain );
                        }
                    }
                    catch ( Exception e )
                    {
                        LOGGER.error( "Failed to create [" + clazz.getSimpleName() + "] [" + ( importCount ) + "/"
                                + totalResults + "] [" + domain + "] [" + e.getMessage() + "]" );
                    }
                }
            }

            page++;
            offset += count;
        }

        LOGGER.info( "Importing [" + clazz.getSimpleName() + "] - END" );

    }

    private void createSettop( SettopDesc settopDesc )
    {
        // LOGGER.info( settopDesc.toString() );

        /*
         * if ( ( null != settopDesc.getExtraProperties() ) && ( !(
         * settopDesc.getExtraProperties().isEmpty() ) ) ) { LOGGER.info(
         * "settopDesc.getExtraProperties()= " + settopDesc.getExtraProperties()
         * ); String contentTypeName = settopDesc.getExtraProperties().get(
         * CONTENT_TYPE ); LOGGER.info( "contentTypeName= " + contentTypeName );
         * }
         */

        LOGGER.info( "contentTypeName= " + settopDesc.getContent() );
    }
}
