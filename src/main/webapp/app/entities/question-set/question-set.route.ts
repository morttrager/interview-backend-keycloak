import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestionSet, QuestionSet } from 'app/shared/model/question-set.model';
import { QuestionSetService } from './question-set.service';
import { QuestionSetComponent } from './question-set.component';
import { QuestionSetDetailComponent } from './question-set-detail.component';
import { QuestionSetUpdateComponent } from './question-set-update.component';

@Injectable({ providedIn: 'root' })
export class QuestionSetResolve implements Resolve<IQuestionSet> {
  constructor(private service: QuestionSetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionSet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questionSet: HttpResponse<QuestionSet>) => {
          if (questionSet.body) {
            return of(questionSet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionSet());
  }
}

export const questionSetRoute: Routes = [
  {
    path: '',
    component: QuestionSetComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionSets',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuestionSetDetailComponent,
    resolve: {
      questionSet: QuestionSetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionSets',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuestionSetUpdateComponent,
    resolve: {
      questionSet: QuestionSetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionSets',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuestionSetUpdateComponent,
    resolve: {
      questionSet: QuestionSetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionSets',
    },
    canActivate: [UserRouteAccessService],
  },
];
