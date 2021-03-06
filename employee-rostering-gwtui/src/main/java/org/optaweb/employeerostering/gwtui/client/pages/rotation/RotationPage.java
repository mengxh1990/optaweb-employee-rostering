/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
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

package org.optaweb.employeerostering.gwtui.client.pages.rotation;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import elemental2.promise.Promise;
import org.jboss.errai.ui.client.local.api.elemental2.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.optaweb.employeerostering.gwtui.client.header.HeaderView;
import org.optaweb.employeerostering.gwtui.client.pages.Page;
import org.optaweb.employeerostering.gwtui.client.tenant.TenantStore;
import org.optaweb.employeerostering.gwtui.client.viewport.CSSGlobalStyle;
import org.optaweb.employeerostering.gwtui.client.viewport.CSSGlobalStyle.GridVariables;

@Templated
public class RotationPage implements IsElement,
                                     Page {

    @Inject
    @DataField("viewport")
    private RotationPageViewport viewport;

    @Inject
    private RotationPageViewportBuilder viewportBuilder;

    @Inject
    private HeaderView headerView;

    @Inject
    private RotationToolbar toolbar;

    @Inject
    private CSSGlobalStyle cssGlobalStyle;

    @PostConstruct
    public void init() {
        cssGlobalStyle.setGridVariable(GridVariables.GRID_UNIT_SIZE, 10);
        cssGlobalStyle.setGridVariable(GridVariables.GRID_ROW_SIZE, 50);
        cssGlobalStyle.setGridVariable(GridVariables.GRID_SOFT_LINE_INTERVAL, 4);
        cssGlobalStyle.setGridVariable(GridVariables.GRID_HARD_LINE_INTERVAL, 24);
        cssGlobalStyle.setGridVariable(GridVariables.GRID_HEADER_COLUMN_WIDTH, 120);
    }

    @Override
    public Promise<Void> onOpen() {
        headerView.addStickyElement(toolbar);
        return refresh();
    }

    public void onTenantChanged(@Observes final TenantStore.TenantChange tenant) {
        refresh();
    }

    private Promise<Void> refresh() {
        return viewportBuilder.buildRotationViewport(viewport);
    }
}
