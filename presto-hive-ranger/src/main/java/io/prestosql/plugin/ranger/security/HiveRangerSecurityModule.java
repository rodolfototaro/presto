/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.plugin.ranger.security;

import com.google.inject.Binder;
import com.google.inject.Scopes;
import io.airlift.configuration.AbstractConfigurationAwareModule;
import io.prestosql.plugin.hive.metastore.SemiTransactionalHiveMetastore;
import io.prestosql.plugin.hive.security.AccessControlMetadata;
import io.prestosql.plugin.hive.security.AccessControlMetadataFactory;
import io.prestosql.spi.connector.ConnectorAccessControl;

public class HiveRangerSecurityModule
        extends AbstractConfigurationAwareModule
{
    @Override
    public void setup(Binder binder)
    {
        binder.bind(ConnectorAccessControl.class).to(RangerConnectorAccessControl.class).in(Scopes.SINGLETON);
        binder.bind(AccessControlMetadataFactory.class).to(HiveRangerSecurityModule.RangerAccessControlMetadataFactory.class);
    }

    private static final class RangerAccessControlMetadataFactory
            implements AccessControlMetadataFactory
    {
        public RangerAccessControlMetadataFactory() {}

        @Override
        public AccessControlMetadata create(SemiTransactionalHiveMetastore metastore)
        {
            return new RangerAccessControlMetadata(metastore);
        }
    }
}
