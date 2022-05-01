import { NgModule } from '@angular/core';

import { SharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { DurationPipe } from './date/duration.pipe';
import { FormatMediumDatetimePipe } from './date/format-medium-datetime.pipe';
import { FormatMediumDatePipe } from './date/format-medium-date.pipe';
import { SortByDirective } from './sort/sort-by.directive';
import { SortDirective } from './sort/sort.directive';
import { ItemCountComponent } from './pagination/item-count.component';
import {
  TuiAccordionModule,
  TuiCheckboxLabeledModule,
  TuiFieldErrorModule,
  TuiInputModule,
  TuiInputNumberModule,
  TuiInputPasswordModule,
  TuiRadioBlockModule,
} from '@taiga-ui/kit';
import {
  TuiButtonModule,
  TuiDialogModule,
  TuiHintControllerModule,
  TuiLoaderModule,
  TuiNotificationModule,
  TuiTextfieldControllerModule,
} from '@taiga-ui/core';
import { TuiAutoFocusModule } from '@taiga-ui/cdk';

@NgModule({
  imports: [SharedLibsModule],
  declarations: [
    AlertComponent,
    AlertErrorComponent,
    HasAnyAuthorityDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    SortByDirective,
    SortDirective,
    ItemCountComponent,
  ],
  exports: [
    SharedLibsModule,
    AlertComponent,
    AlertErrorComponent,
    HasAnyAuthorityDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    SortByDirective,
    SortDirective,
    ItemCountComponent,
    TuiInputModule,
    TuiFieldErrorModule,
    TuiTextfieldControllerModule,
    TuiHintControllerModule,
    TuiInputPasswordModule,
    TuiButtonModule,
    TuiNotificationModule,
    TuiRadioBlockModule,
    TuiCheckboxLabeledModule,
    TuiDialogModule,
    TuiAccordionModule,
    TuiLoaderModule,
    TuiInputNumberModule,
    TuiAutoFocusModule,
  ],
})
export class SharedModule {}
